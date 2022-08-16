package com.dev.tweetanalyzer.controllers;

import co.elastic.clients.elasticsearch.ml.Page;
import com.dev.tweetanalyzer.model.SimpleTweet;
import com.dev.tweetanalyzer.repository.TweetElasticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InterestingTweets {
    @Autowired
    TweetElasticRepository tweetElasticRepository;

    @GetMapping("/tweet/{id}")
    List<SimpleTweet> getConversation(@PathVariable String id){
        List<SimpleTweet> lst = tweetElasticRepository.findConversation(id, PageRequest.of(0,100)).stream().toList();
        return lst;
    }


    @GetMapping("/tweets/{page}")
    List<SimpleTweet> getInterestingTweets(@PathVariable int page){
        List<SimpleTweet> lst = tweetElasticRepository.findRecentTweets(PageRequest.of(page, 10)).stream().toList();
        return lst;
    }
}
