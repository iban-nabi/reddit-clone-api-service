package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @GetMapping("/{postId}/all")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentRepository.getCommentsFromPost(postId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        return ResponseEntity.ok(commentRepository.getComment(id));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        commentRepository.save(comment);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody Comment comment) {
        commentRepository.update(comment);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        commentRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
