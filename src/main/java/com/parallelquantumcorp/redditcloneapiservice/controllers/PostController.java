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
        return ResponseEntity.ok(postRepository.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> one(@PathVariable Long id) {
        return ResponseEntity.ok(postRepository.getPost(id));
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable String query) {
        return ResponseEntity.ok(postRepository.searchPost(query));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPost(@RequestBody Post post) {
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody Post post) {
        postRepository.update(post);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        postRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
