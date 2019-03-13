package com.bubble.sms.grpc;

import com.bubble.common.grcp.AbstractGrpcServerInitializer;
import com.bubble.common.grcp.TransmitBizExceptionInterceptor;
import com.google.rpc.Status;
import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.protobuf.StatusProto;
import io.grpc.util.TransmitStatusRuntimeExceptionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GrpcServerInitializer extends AbstractGrpcServerInitializer implements ApplicationRunner {


    @Autowired
    private List<BindableService> services;

    @Value("${grpc.server.port:8090}")
    private int port;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
    }

    @Override
    public List<BindableService> getServices() {
        return services;
    }

    @Override
    public int getPort() {
        return port;
    }
}
