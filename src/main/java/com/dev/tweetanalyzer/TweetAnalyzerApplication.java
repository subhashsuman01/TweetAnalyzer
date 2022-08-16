package com.dev.tweetanalyzer;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ReactiveElasticsearchRestClientAutoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication(exclude = ReactiveElasticsearchRestClientAutoConfiguration.class)
@EnableMongoRepositories
@EnableReactiveMongoRepositories
public class TweetAnalyzerApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(TweetAnalyzerApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) {
	}
}
