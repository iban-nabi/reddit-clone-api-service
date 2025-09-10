package com.jie.redditcloneapiservice.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequest {
    private Long parentCommentId;

    @NotBlank(message = "Comment is required")
    private String content;
}
