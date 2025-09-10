package com.jie.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Post extends Content {
    private Long id;
    private SubReddit subReddit;
    private String title;
    private String content;
    private String tag;
}
