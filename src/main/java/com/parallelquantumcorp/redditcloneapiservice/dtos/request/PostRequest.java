package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    private String title;
    private String content;
    private String tag;
}
