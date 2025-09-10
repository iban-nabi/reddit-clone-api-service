package com.jie.redditcloneapiservice.controllers;

import com.jie.redditcloneapiservice.dtos.response.CommentResponse;
import com.jie.redditcloneapiservice.dtos.request.CommentRequest;
import com.jie.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.jie.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.jie.redditcloneapiservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling Comment-related operations.
 * This controller provides endpoints for managing comments in a Reddit-like application.
 *
 * Endpoints include:
 * - Getting all comments for a specific post
 * - Getting a specific comment by ID
 * - Creating a new comment on a post
 * - Updating an existing comment
 * - Soft deleting a comment
 * - Upvoting a comment
 * - Downvoting a comment
 *
 * All operations that involve finding resources may throw ResourceNotFoundException
 * if the requested resource does not exist.
 *
 * @RestController Marks this class as a REST controller
 * @RequestMapping("/api/comment") Base path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/comment")
@AllArgsConstructor
public class CommentController {

    // service
    private final CommentService commentService;

    @GetMapping("/{postId}/all")
    public ResponseEntity<?> getAllCommentsFromPost(@PathVariable Long postId) {
        List<CommentResponse> comments = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        try {
            CommentResponse comment = commentService.getComment(id);
            return ResponseEntity.ok(comment);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/{postId}/create")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        try {
            commentService.createComment(postId, commentRequest);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody CommentUpdateRequest commentUpdateRequest) {
        try {
            commentService.updateComment(id, commentUpdateRequest);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/upvote/{id}")
    public ResponseEntity<?> upVoteComment(@PathVariable Long id){
        try {
            commentService.upvoteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/downvote/{id}")
    public ResponseEntity<?> downVoteComment(@PathVariable Long id){
        try {
            commentService.downvoteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
