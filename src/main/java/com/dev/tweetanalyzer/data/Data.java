package com.dev.tweetanalyzer.data;
import com.fasterxml.jackson.annotation.*;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

public record Data(String id, String text, List<ReferencedTweet> referenced_tweets, OffsetDateTime created_at) {

    @JsonCreator
    public Data(@JsonProperty("id") String id,
                @JsonProperty("text") String text,
                @JsonProperty("referenced_tweets") List<ReferencedTweet> referenced_tweets,
                @JsonProperty("created_at") OffsetDateTime created_at) {
        this.id = id;
        this.text = text;
        this.referenced_tweets = referenced_tweets;
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", referenced_tweets=" + referenced_tweets +
                ", created_at=" + created_at +
                '}';
    }
}
