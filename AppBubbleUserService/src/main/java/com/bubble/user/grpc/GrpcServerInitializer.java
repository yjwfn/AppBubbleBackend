package com.bubble.user.grpc;

import com.bubble.common.exception.TransmitBizExceptionInterceptor;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrpcServerInitializer implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(GrpcServerInitializer.class.getName());

    @Autowired
    private List<BindableService> services;

    @Value("${grpc.server.port:8090}")
    private int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.trace("Start gRPC server.");
        logger.trace("Found Services {}" , services);
        ServerBuilder serverBuilder = ServerBuilder
                .forPort(port);

        if (services != null && !services.isEmpty()) {
            for (BindableService bindableService : services) {
                serverBuilder.addService(bindableService);
            }
        }

        logger.trace("Register TransmitBizExceptionInterceptor.");
        serverBuilder.intercept(new TransmitBizExceptionInterceptor());
        logger.trace("Register TransmitStatusRuntimeExceptionInterceptor.");
        serverBuilder.intercept(TransmitStatusRuntimeExceptionInterceptor.instance());
        Server server = serverBuilder.build();
        server.start();

        logger.trace("Server running on {}", port);
        startDaemonAwaitThread(server);
    }


    private void startDaemonAwaitThread(Server server) {
        Thread awaitThread = new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
                logger.error("gRPC server stopped.", e);
            }
        });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
