package com.bubble.user.relation.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrpcServerInitializer implements ApplicationRunner {


    @Autowired
    private List<BindableService> services;

    @Value("${grpc.server.port:8090}")
    private int port;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ServerBuilder serverBuilder = ServerBuilder
                .forPort(port);

        if (services != null && !services.isEmpty()) {
            for (BindableService bindableService : services) {
                serverBuilder.addService(bindableService);
            }
        }
        Server server = serverBuilder.build();
        serverBuilder.intercept(TransmitStatusRuntimeExceptionInterceptor.instance());
        server.start();
        startDaemonAwaitThread(server);
    }


    private void startDaemonAwaitThread(Server server) {
        Thread awaitThread = new Thread(() -> {
            try {
                server.awaitTermination();
            } catch (InterruptedException e) {
//                log.error("gRPC server stopped.", e);
            }
        });
        awaitThread.setDaemon(false);
        awaitThread.start();
    }
}
