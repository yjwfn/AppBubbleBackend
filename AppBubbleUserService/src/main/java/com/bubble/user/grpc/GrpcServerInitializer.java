package com.bubble.user.grpc;

import com.bubble.common.exception.TransmitBizExceptionInterceptor;
import com.google.common.base.Splitter;
import io.grpc.*;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


import java.util.List;

import static com.bubble.user.grpc.Keys.*;

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

        serverBuilder.intercept(new ServerInterceptor() {
            @Override
            public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> call, Metadata headers, ServerCallHandler<ReqT, RespT> next) {
                logger.trace("Receive headers from client {} ", headers);

                String headerValue = headers.get(METADATA_KEY_HEADER_AUTHORIZATION);
                String[] bearer = headerValue != null ? headerValue.split(" ") : null;
                logger.trace("Header value {} for key {}", headerValue, METADATA_KEY_HEADER_AUTHORIZATION);

                Context context = Context.current();
                if(bearer != null && bearer.length >= 2 && !Strings.isBlank(bearer[1])){
                    context = Context.current().withValue(CONTEXT_KEY_TOKEN, bearer[1]);
                    logger.trace("Token {} ", bearer[1]);
                }

                return Contexts.interceptCall(context, call,headers, next);
            }
        });


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
