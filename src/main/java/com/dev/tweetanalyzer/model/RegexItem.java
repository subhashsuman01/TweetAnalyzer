package com.dev.tweetanalyzer.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("regex")
public class RegexItem {
    @Id
    private final String id;
    private final String regex;

    public RegexItem(String id, String regex) {
        this.id = id;
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "RegexItem{" +
                "id='" + id + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
