package com.dev.tweetanalyzer.repository;

import com.dev.tweetanalyzer.model.SimpleTweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TweetElasticRepository extends ElasticsearchRepository<SimpleTweet, String> {
    @Query("{\"match_all\": {}}")
    Page<SimpleTweet> findRecentTweets(Pageable pageable);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"parentId\": \"?0\"}}]}}")
    Page<SimpleTweet> findConversation(String parentId, Pageable pageable);
}
