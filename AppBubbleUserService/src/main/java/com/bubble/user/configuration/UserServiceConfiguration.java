package com.bubble.user.configuration;

import com.bubble.common.snowflake.SequenceGenerator;
import com.bubble.common.snowflake.impl.IdWorkerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceConfiguration {


    @Bean
    public SequenceGenerator getSequenceGenerator(){
        return new IdWorkerImpl(1, 1);
    }
}
