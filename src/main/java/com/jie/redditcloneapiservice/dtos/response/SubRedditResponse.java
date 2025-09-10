package com.jie.redditcloneapiservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubRedditResponse {
    private Long id;
    private String name;
    private String description;
}
