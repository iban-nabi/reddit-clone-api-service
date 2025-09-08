package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubRedditRequest {
    @NotBlank(message = "Sub Reddit description is required")
    private String description;
}
