package com.dev.tweetanalyzer;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GlobalConfig {
    @Bean
    public ConcurrentHashMap<String,String> getRegexMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ObjectMapper getTweetMapper(){
        return new ObjectMapper().findAndRegisterModules().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
