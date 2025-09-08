package com.parallelquantumcorp.redditcloneapiservice.dtos.request;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
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
