package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePostRequest {
    @NotBlank(message = "Post content is required")
    private String content;
}
