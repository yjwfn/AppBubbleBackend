package com.bubble.sms.grpc;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
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

//    @Value("#{server.port}")
    private int port = 8090;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        ServerBuilder serverBuilder = ServerBuilder
                .forPort(port);

        if(services != null && !services.isEmpty()){
            for(BindableService bindableService: services){
                serverBuilder.addService(bindableService);
            }
        }

        Server server = serverBuilder.build();
        server.start();
    }
}