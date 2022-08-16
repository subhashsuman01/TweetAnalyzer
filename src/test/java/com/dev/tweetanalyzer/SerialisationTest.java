package com.dev.tweetanalyzer;

import com.dev.tweetanalyzer.data.TweetDataWrapper;
import com.dev.tweetanalyzer.model.SimpleTweet;
import com.dev.tweetanalyzer.util.Mapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class SerialisationTest {
    private String tweet = "{\"data\":{\"created_at\":\"2022-08-15T22:42:16.001Z\",\"id\":\"1559309527836725249\",\"text\":\"I wanted to say something but I forgot what I wanted to say so uhh\\n\\nHow is your day (spoiler alert: false. \\\"how\\\" is not \\\"your day\\\")\"}}";

//    @Mock
    ObjectMapper tweetMapper = new ObjectMapper().findAndRegisterModules().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);


    @Test
    public void dateTimeTest(){
        String date = "2022-08-15T22:42:16.001Z";
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        OffsetDateTime offsetDateTime = OffsetDateTime.from(dateTimeFormatter.parse(date));
        assert date.equals(offsetDateTime.toString());
    }

    @Test
    public void serializationTest() throws JsonProcessingException {
        TweetDataWrapper tweetDataWrapper = tweetMapper.readValue(tweet, TweetDataWrapper.class);
        System.out.println(tweetDataWrapper);
        SimpleTweet simpleTweet = Mapper.convertToSimpleTweet(tweetDataWrapper);
        System.out.println(simpleTweet);
        System.out.println(tweetDataWrapper.data().id());
    }

    @Test
    public void redisTest(){

    }
}
