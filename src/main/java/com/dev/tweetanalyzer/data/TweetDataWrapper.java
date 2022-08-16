package com.dev.tweetanalyzer.data;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName("data")
public record TweetDataWrapper(Data data) {

    @JsonCreator
    public TweetDataWrapper(@JsonProperty("data") Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "TweetData{" +
                "data=" + data +
                '}';
    }
}
