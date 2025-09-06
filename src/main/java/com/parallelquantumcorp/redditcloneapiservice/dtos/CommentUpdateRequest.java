package com.parallelquantumcorp.redditcloneapiservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequest {
    private Long id;
    private String content;
}
