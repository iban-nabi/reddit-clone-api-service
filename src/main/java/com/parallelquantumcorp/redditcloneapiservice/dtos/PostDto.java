package com.parallelquantumcorp.redditcloneapiservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private UserDto user;
}
