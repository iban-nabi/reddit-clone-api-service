package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubRedditRequest {
    @NotBlank(message = "Sub Reddit name is required")
    private String name;

    @NotBlank(message = "Sub Reddit description is required")
    private String description;
}
