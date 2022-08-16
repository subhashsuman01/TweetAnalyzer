package com.dev.tweetanalyzer.util;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.dev.tweetanalyzer.model.SimpleTweetDao;

public class Mapper {

    private static String getParentId(TweetDataWrapper tweetDataWrapper){
        if (tweetDataWrapper.data().referenced_tweets() == null || tweetDataWrapper.data().referenced_tweets().size()==0)
            return tweetDataWrapper.data().id();
        return tweetDataWrapper.data().referenced_tweets().get(0).id();
    }

    public static SimpleTweetDao convertToSimpleTweet(TweetDataWrapper tweetDataWrapper){
        return new SimpleTweetDao(
                tweetDataWrapper.data().id(),
                tweetDataWrapper.data().text(),
                tweetDataWrapper.data().created_at(),
                getParentId(tweetDataWrapper)
        );
    }
}
