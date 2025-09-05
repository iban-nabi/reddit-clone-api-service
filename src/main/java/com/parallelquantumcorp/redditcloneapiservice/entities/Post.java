package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Post extends Content {
    private long id;
    private SubReddit subreddit;
    private String title;
    private String content;
    private String tag;
    private User author;
    private final List<Comment> comments = new ArrayList<>();
}
