package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubRedditRequest {
    private String name;
    private String description;
}
