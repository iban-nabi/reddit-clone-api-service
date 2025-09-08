package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostRequest {
    @NotBlank(message = "Post title is required")
    private String title;

    @NotBlank(message = "Post content is required")
    private String content;

    private String tag;
}
