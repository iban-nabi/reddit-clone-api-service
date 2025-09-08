package com.parallelquantumcorp.redditcloneapiservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentResponse {
    private Long id;
    private Long parentCommentId;
    private String content;
    private String username;
    private long karma;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
