package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/{postId}/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentRepository.getComments(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long postId, @PathVariable Long id) {
        return ResponseEntity.ok(commentRepository.getComment(postId, id));
    }

    @PostMapping("/create-comment")
    public ResponseEntity<?> createComment(@PathVariable Long postId, @RequestBody Comment comment) {
        commentRepository.save(postId, comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update-comment")
    public ResponseEntity<?> updateComment(@PathVariable Long postId, @RequestBody Comment comment) {
        commentRepository.update(postId, comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/delete-comment")
    public ResponseEntity<?> deleteComment(@PathVariable Long postId, @PathVariable Long id) {
        commentRepository.delete(postId, id);
        return ResponseEntity.ok().build();
    }
}
