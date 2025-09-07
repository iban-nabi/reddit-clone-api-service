package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentUpdateRequest;
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
        List<CommentDto> comments = commentService.getAllCommentsFromPost(postId);
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        CommentDto comment = commentService.getComment(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        boolean success = commentService.createComment(commentDto);
        if (!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateComment(@PathVariable Long id,
                                           @RequestBody CommentUpdateRequest commentUpdateRequest) {
        boolean success = commentService.updateComment(id, commentUpdateRequest);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean success = commentService.deleteComment(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
