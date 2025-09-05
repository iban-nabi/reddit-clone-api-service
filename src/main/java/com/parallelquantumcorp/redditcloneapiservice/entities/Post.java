package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class Post extends Content {
    private Long id;
    private SubReddit subreddit;
    private String title;
    private String content;
    private String tag;
    private User author;
    private final Map<Long, Comment> comments = new LinkedHashMap<>();
}
