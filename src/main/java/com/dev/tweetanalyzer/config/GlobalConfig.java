package com.dev.tweetanalyzer.config;


import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class GlobalConfig {
    @Autowired
    private Environment env;
    @Bean
    public ConcurrentHashMap<String,String> getRegexMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    public ObjectMapper getTweetMapper(){
        return new ObjectMapper().findAndRegisterModules().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Bean
    public Request getTwitterConnRequest(){
        return new Request.Builder()
                .url(Objects.requireNonNull(env.getProperty("twitter.url")))
                .addHeader("Authorization", "Bearer " + env.getProperty("twitter.access.token"))
                .build();
    }
}
