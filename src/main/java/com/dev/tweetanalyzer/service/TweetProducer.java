package com.dev.tweetanalyzer.service;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TweetProducer implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(TweetProducer.class);
    @Autowired
    private Environment env;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    ObjectMapper tweetMapper;

    @Autowired
    private Request request;

    @Async
    private void start() throws IOException {

        OkHttpClient client = new OkHttpClient();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            while(!response.body().source().exhausted()){
                String inputLine;
                while ((inputLine = response.body().source().readUtf8Line()) != null){
                    try {
                        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(inputLine, TweetDataWrapper.class);

                        if (tweetDataWrapper.data().referenced_tweets() != null)
                            kafkaTemplate.send("reply", tweetDataWrapper.data().id(), inputLine);
                        else
                            kafkaTemplate.send("tweet", tweetDataWrapper.data().id(), inputLine);
                    } catch (MismatchedInputException e){
                        logger.warn(e.getMessage());
                    } catch (IOException e){
                        throw new IOException(e);
                    }
                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
        start();
    }
}
