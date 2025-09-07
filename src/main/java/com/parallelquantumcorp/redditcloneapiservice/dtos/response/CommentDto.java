package com.parallelquantumcorp.redditcloneapiservice.dtos.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDto {
    private Long id;
    private CommentDto parent;
    private String content;
    private UserResponse user;
    private int upvotes;
    private int downvotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
