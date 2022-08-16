package com.dev.tweetanalyzer.service;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.dev.tweetanalyzer.model.SimpleTweet;
import com.dev.tweetanalyzer.repository.TweetElasticRepository;
import com.dev.tweetanalyzer.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;

@Service
public class TweetConsumer {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    MongodbService mongodbService;

    @Autowired
    TweetElasticRepository tweetElasticRepository;

    @Autowired
    ObjectMapper tweetMapper;

    @KafkaListener(topics = "tweet", groupId = "tweet")
    public void listenToTweets(String key, String msg) throws JsonProcessingException {
        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(msg, TweetDataWrapper.class);
        if(mongodbService.isInteresting(tweetDataWrapper.data().text())){
            redisTemplate.opsForSet().add("interesting:tweet", key);
            SimpleTweet simpleTweet = Mapper.convertToSimpleTweet(tweetDataWrapper);
            tweetElasticRepository.save(simpleTweet);
        }

    }

    @KafkaListener(topics = "reply", groupId = "reply")
    public void listenToReplies(String key, String msg) throws JsonProcessingException {
        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(msg, TweetDataWrapper.class);
        if(redisTemplate.opsForSet().isMember("interesting:tweet", key)){
            SimpleTweet simpleTweet = Mapper.convertToSimpleTweet(tweetDataWrapper);
            tweetElasticRepository.save(simpleTweet);
        }
    }
}
