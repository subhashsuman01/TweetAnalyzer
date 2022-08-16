package com.dev.tweetanalyzer;

import com.dev.tweetanalyzer.repository.RegexRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.runtime.ObjectMethods;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication(exclude = ReactiveElasticsearchRestClientAutoConfiguration.class)
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class TweetAnalyzerApplication implements ApplicationRunner {

	@Bean
	public ConcurrentHashMap<String,String> getRegexMap(){
		return new ConcurrentHashMap<>();
	}

	@Bean
	public ObjectMapper getTweetMapper(){
		return new ObjectMapper().findAndRegisterModules().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public static void main(String[] args) {
		SpringApplication.run(TweetAnalyzerApplication.class, args);
	}

	@Autowired
	RegexRepository regexRepository;

	@Autowired
	RedisTemplate redisTemplate;

	@Override
	public void run(ApplicationArguments args) {

	}
}
