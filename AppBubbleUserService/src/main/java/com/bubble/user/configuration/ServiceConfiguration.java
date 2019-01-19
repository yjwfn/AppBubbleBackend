package com.bubble.user.configuration;

import com.bubble.sms.grpc.service.SmsServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfiguration {


    @Bean
    public SmsServiceGrpc.SmsServiceBlockingStub getSmsService(){
        ManagedChannelBuilder builder = ManagedChannelBuilder.forAddress("sms", 8090);
        builder.usePlaintext();
        return SmsServiceGrpc.newBlockingStub(builder.build());
    }
}
