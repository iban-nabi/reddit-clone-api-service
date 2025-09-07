package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Comment extends Content {
    private Long id;
    private Post post;
    private Comment parent;
    private String content;
}
