package com.bubble.common.grcp;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public abstract class AbstractGrpcServerInitializer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * 获取服务列表
     * @return
     */
    public abstract List<BindableService> getServices();


    /**
     * 获取gRPC服务端口
     * @return
     */
    public abstract int getPort();


    /**
     * 启动服务
     */
    protected void start() throws IOException {
        logger.debug("Start gRPC server.");
        logger.debug("Found Services {}" , getServices());

        ServerBuilder serverBuilder = ServerBuilder
                .forPort(getPort());

        List<BindableService> services = getServices();
        if (services != null && !services.isEmpty()) {
            for (BindableService bindableService : services) {
                serverBuilder.addService(bindableService);
            }
        }
        logger.debug("Register TransmitBizExceptionInterceptor.");
        serverBuilder.intercept(new TransmitBizExceptionInterceptor());
        Server server = serverBuilder.build();
        server.start();
        startDaemonAwaitThread(server);
        logger.debug("Server running on {}", getPort());
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
