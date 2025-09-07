package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.PostDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UpdatePostRequest;
import com.parallelquantumcorp.redditcloneapiservice.service.PostService;
import lombok.AllArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/all/{subRedditName}")
    public ResponseEntity<?> getAllSubRedditPosts(@PathVariable String subRedditName) {
        List<PostDto> posts = postService.getAllSubRedditPosts(subRedditName);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id) ;
        if(post==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(post);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchPost(@PathVariable String query) {
        List<PostDto> posts = postService.searchPost(query);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{subRedditName}/create")
    public ResponseEntity<?> createPostInSubReddit(@PathVariable String subRedditName,
                                                   @RequestBody PostDto postDto) {
        postService.createPost(postDto, subRedditName);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPostGlobal(@RequestBody PostDto postDto) {
        postService.createPost(postDto, null);
        return ResponseEntity.ok().build();
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody UpdatePostRequest updatePostRequest) {
        boolean success = postService.updatePost(id, updatePostRequest);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        boolean  success = postService.deletePost(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
