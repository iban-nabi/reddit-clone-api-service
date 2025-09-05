package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        return ResponseEntity.ok(postRepository.getPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Long id) {
        return ResponseEntity.ok(postRepository.getPost(id));
    }

    @PostMapping("/create-post")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/update-post")
    public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody Post post) {
        postRepository.update(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/delete-post")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
