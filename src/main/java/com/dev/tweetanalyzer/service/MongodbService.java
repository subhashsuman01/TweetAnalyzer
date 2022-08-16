package com.dev.tweetanalyzer.service;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.dev.tweetanalyzer.model.RegexItem;
import com.dev.tweetanalyzer.repository.RegexRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.ChangeStreamEvent;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Service
public class MongodbService implements CommandLineRunner {
    Logger logger = LoggerFactory.getLogger(MongodbService.class);
    @Autowired
    ConcurrentHashMap<String, String> regexMap;

    @Autowired
    RegexRepository regexRepository;

    @Autowired
    ReactiveMongoTemplate reactiveMongoTemplate;

    public void init(){
        regexRepository.findAll().forEach(
                regexItem -> regexMap.put(regexItem.getId(), regexItem.getRegex()));
        System.out.println(regexMap.toString());
    }

    public boolean isInteresting(String tweetText) {
        Boolean val = regexMap.search(1L, (key, value) -> {
            if (Pattern.matches(value, tweetText))
                return true;
            else return null;
        });
        return val != null;
    }

    // reactively updates the local regexMap according to changes in database
    public void sync() {
        Flux<ChangeStreamEvent<RegexItem>> flux = reactiveMongoTemplate.changeStream(RegexItem.class).watchCollection("regex").listen();
        flux.subscribe(changeStreamEvent -> {
            logger.info("Received changeStreamEvent: {}", changeStreamEvent);
            RegexItem regexItem = changeStreamEvent.getBody();
            try {
                switch (Objects.requireNonNull(changeStreamEvent.getOperationType())) {
                    case INSERT, UPDATE -> regexMap.put(regexItem.getId(), regexItem.getRegex());
                    case DELETE -> regexMap.remove(regexItem.getId());
                }
            } catch (Exception e) {
                logger.error("Error while updating regexMap. Either key not present or illegal event", e);
            }
        });
    }

    @Override
    public void run(String... args) throws Exception {
        init();
        sync();
    }
}
