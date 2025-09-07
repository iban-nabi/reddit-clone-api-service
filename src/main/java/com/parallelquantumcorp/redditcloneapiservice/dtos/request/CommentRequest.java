package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private CommentResponse parent;
    private String content;
    private UserResponse user;
}
