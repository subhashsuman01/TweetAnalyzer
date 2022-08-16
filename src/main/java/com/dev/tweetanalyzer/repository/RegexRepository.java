package com.dev.tweetanalyzer.repository;

import com.dev.tweetanalyzer.model.RegexItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegexRepository extends MongoRepository<RegexItem, String> {

}
