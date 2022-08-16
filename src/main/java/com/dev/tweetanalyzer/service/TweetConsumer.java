package com.dev.tweetanalyzer.service;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.dev.tweetanalyzer.model.SimpleTweetDao;
import com.dev.tweetanalyzer.repository.TweetElasticRepository;
import com.dev.tweetanalyzer.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class TweetConsumer {

    Logger logger = LoggerFactory.getLogger(TweetConsumer.class);

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    MongodbService mongodbService;

    @Autowired
    TweetElasticRepository tweetElasticRepository;

    @Autowired
    ObjectMapper tweetMapper;

    @KafkaListener(topics = "tweet", groupId = "tweet")
    public void listenToTweets(String msg) throws JsonProcessingException {
        logger.info("Received tweet: {}", msg);
        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(msg, TweetDataWrapper.class);
        if(mongodbService.isInteresting(tweetDataWrapper.data().text())){
            redisTemplate.opsForSet().add("interesting:tweet", tweetDataWrapper.data().id());
            SimpleTweetDao simpleTweetDao = Mapper.convertToSimpleTweet(tweetDataWrapper);
            tweetElasticRepository.save(simpleTweetDao);
        }

    }

    @KafkaListener(topics = "reply", groupId = "reply")
    public void listenToReplies(String msg) throws JsonProcessingException {
        logger.info("Received reply: {}", msg);
        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(msg, TweetDataWrapper.class);
        if(Boolean.TRUE.equals(redisTemplate.opsForSet().isMember("interesting:tweet", tweetDataWrapper.data().id()))){
            SimpleTweetDao simpleTweetDao = Mapper.convertToSimpleTweet(tweetDataWrapper);
            tweetElasticRepository.save(simpleTweetDao);
        }
    }
}
