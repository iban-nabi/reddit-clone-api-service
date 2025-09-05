package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comment extends Content {
    private long id;
    private Post post;
    private String content;
}
