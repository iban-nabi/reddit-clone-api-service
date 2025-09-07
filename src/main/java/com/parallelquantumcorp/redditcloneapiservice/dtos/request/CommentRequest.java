package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private CommentDto parent;
    private String content;
    private UserResponse user;
}
