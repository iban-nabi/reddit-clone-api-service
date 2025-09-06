package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@AllArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @GetMapping("/{postId}/all")
    public ResponseEntity<?> getAllComments(@PathVariable Long postId) {
        List<CommentDto> comments = commentRepository.getCommentsFromPost(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        Comment comment = commentRepository.getComment(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(commentMapper.toDto(comment));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createComment(@RequestBody CommentDto commentDto) {
        commentRepository.save(commentDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateComment(@RequestBody CommentUpdateRequest request) {
        boolean success = commentRepository.update(request);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> deleteComment(@PathVariable Long id) {
        boolean success = commentRepository.delete(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
