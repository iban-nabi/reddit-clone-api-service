package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.PostResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.PostRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdatePostRequest;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/post")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        List<PostResponse> posts = postService.getAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/all/{subRedditName}")
    public ResponseEntity<?> getAllSubRedditPosts(@PathVariable String subRedditName) {
        List<PostResponse> posts = postService.getAllSubRedditPosts(subRedditName);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Long id) {
        try {
            PostResponse post = postService.getPostById(id) ;
            return ResponseEntity.ok(post);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchPost(@PathVariable String query) {
        List<PostResponse> posts = postService.searchPost(query);
        return ResponseEntity.ok(posts);
    }

    @PostMapping("/{subRedditName}/create")
    public ResponseEntity<?> createPostInSubReddit(@PathVariable String subRedditName,
                                                   @RequestBody PostRequest postRequest) {
        try{
            postService.createPost(postRequest, subRedditName);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createPostGlobal(@RequestBody PostRequest postRequest) {
        try{
            postService.createPost(postRequest, null);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updatePost(@PathVariable Long id,
                                        @RequestBody UpdatePostRequest updatePostRequest) {
        try{
            postService.updatePost(id, updatePostRequest);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PatchMapping("/delete/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        try{
            postService.deletePost(id);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PatchMapping("/upvote/{id}")
    public ResponseEntity<?> upVotePost(@PathVariable Long id){
        try{
            postService.upvotePost(id);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PatchMapping("/downvote/{id}")
    public ResponseEntity<?> downVotePost(@PathVariable Long id){
        try{
            postService.downvotePost(id);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }
}
