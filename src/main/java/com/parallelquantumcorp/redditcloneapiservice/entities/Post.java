package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.util.LinkedHashMap;
import java.util.Map;

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
