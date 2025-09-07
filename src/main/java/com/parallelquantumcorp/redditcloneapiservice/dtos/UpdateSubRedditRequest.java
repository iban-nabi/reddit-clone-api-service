package com.parallelquantumcorp.redditcloneapiservice.dtos;

import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateSubRedditRequest {
    private String description;
}
