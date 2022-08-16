package com.dev.tweetanalyzer.controllers;

import com.dev.tweetanalyzer.model.SimpleTweetDao;
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
    List<SimpleTweetDao> getConversation(@PathVariable String id){
        List<SimpleTweetDao> lst = tweetElasticRepository.findConversation(id, PageRequest.of(0,100)).stream().toList();
        return lst;
    }


    @GetMapping("/tweets/{page}")
    List<SimpleTweetDao> getInterestingTweets(@PathVariable int page){
        List<SimpleTweetDao> lst = tweetElasticRepository.findRecentTweets(PageRequest.of(page, 10)).stream().toList();
        return lst;
    }
}
