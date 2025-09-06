package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.PostDto;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.mappers.PostMapper;
import com.parallelquantumcorp.redditcloneapiservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
@AllArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final PostMapper postMapper;

    @GetMapping("/all")
    public ResponseEntity<?> all() {
        List<PostDto> posts = postRepository.getAllPosts()
                .stream()
                .map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        Post post = postRepository.getPost(id);
        if(post==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postMapper.toDto(post));
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> search(@PathVariable String query) {
        List<PostDto> posts = postRepository.searchPost(query)
                .stream()
                .map(postMapper::toDto)
                .toList();
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{subRedditName}/create")
    public ResponseEntity<?> createPostInSubReddit(@PathVariable String subRedditName,
                                                   @RequestBody PostDto postDto) {
        Post post = postService.createPost(postDto, subRedditName);
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPostGlobal(@RequestBody PostDto postDto) {
        Post post = postService.createPost(postDto, null);
        postRepository.save(post);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody PostDto post) {
        boolean success = postRepository.update(postMapper.toEntity(post));
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        boolean  success = postRepository.delete(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
