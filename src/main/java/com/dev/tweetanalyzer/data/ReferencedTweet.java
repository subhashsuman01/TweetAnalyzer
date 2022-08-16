package com.dev.tweetanalyzer.data;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ReferencedTweet(String type, String id) {
    @JsonCreator
    public ReferencedTweet(@JsonProperty("type") String type, @JsonProperty("id") String id) {
        this.type = type;
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReferencedTweet{" +
                "type='" + type + '\'' +
                ", id=" + id +
                '}';
    }
}
