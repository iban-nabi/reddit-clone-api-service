package com.parallelquantumcorp.redditcloneapiservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponse {
    private Long id;
    private String subReddit;
    private String title;
    private String content;
    private String tag;
    private String username;
    private int upvotes;
    private int downvotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
