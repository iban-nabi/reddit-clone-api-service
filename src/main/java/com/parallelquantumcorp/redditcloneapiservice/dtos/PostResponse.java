package com.parallelquantumcorp.redditcloneapiservice.dtos;

import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String tag;
    private UserDto user;
}
