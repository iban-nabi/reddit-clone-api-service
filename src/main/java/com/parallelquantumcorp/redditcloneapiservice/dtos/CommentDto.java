package com.parallelquantumcorp.redditcloneapiservice.dtos;

import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class CommentDto {
    private Long id;
    private Post post;
    private String content;
    private User user;
    private int upvotes;
    private int downvotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
