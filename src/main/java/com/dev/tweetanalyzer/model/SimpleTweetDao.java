package com.dev.tweetanalyzer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.OffsetDateTime;

@Document(indexName = "tweets")
public class SimpleTweetDao {
    @Id
    final private String id;
    final private String text;

    @Field(type = FieldType.Date, format = DateFormat.date_time)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    final private OffsetDateTime time;
    final private String parentId;

    public SimpleTweetDao(String id, String text, OffsetDateTime time, String parentId){
        this.id = id;
        this.text = text;
        this.time = time;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public OffsetDateTime getTime() {
        return time;
    }

    public String getParentId() {
        return parentId;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SimpleTweet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", time=" + time +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
