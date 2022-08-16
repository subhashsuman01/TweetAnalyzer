package com.dev.tweetanalyzer.service;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class TweetProducer implements CommandLineRunner {
    @Autowired
    private Environment env;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper tweetMapper;

    @Async
    @Override
    public void run(String... args) throws Exception {
//        URL url = new URL(env.getProperty("twitter.url"));
//        HttpURLConnection twitterConnection = (HttpURLConnection) url.openConnection();
//        twitterConnection.setRequestMethod("GET");
//        twitterConnection.setRequestProperty("Authorization", "Bearer " + env.getProperty("twitter.access.token"));
//
//        try (BufferedReader tweetStream = new BufferedReader(new InputStreamReader(twitterConnection.getInputStream()))) {
//            String inputLine;
//            while ((inputLine = tweetStream.readLine()) != null) {
//                System.out.println(inputLine);
//                if (Thread.interrupted()) {
//                    kafkaTemplate.flush();
//                    break;
//                }
//                TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(inputLine, TweetDataWrapper.class);
//                if (tweetDataWrapper.data().referenced_tweets() != null)
//                    kafkaTemplate.send("reply", tweetDataWrapper.data().id().toString(), inputLine);
//                else
//                    kafkaTemplate.send("tweet", tweetDataWrapper.data().id().toString(), inputLine);
//            }
//        }
//        finally {
//            twitterConnection.disconnect();
//        }
    }
}
