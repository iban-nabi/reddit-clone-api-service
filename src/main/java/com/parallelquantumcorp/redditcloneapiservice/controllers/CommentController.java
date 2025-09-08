package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{postId}/create")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        try {
            commentService.createComment(postId, commentRequest);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody CommentUpdateRequest commentUpdateRequest) {
        try {
            commentService.updateComment(id, commentUpdateRequest);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/upvote/{id}")
    public ResponseEntity<?> upVoteComment(@PathVariable Long id){
        try {
            commentService.upvoteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/downvote/{id}")
    public ResponseEntity<?> downVoteComment(@PathVariable Long id){
        try {
            commentService.downvoteComment(id);
            return ResponseEntity.ok().build();

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
