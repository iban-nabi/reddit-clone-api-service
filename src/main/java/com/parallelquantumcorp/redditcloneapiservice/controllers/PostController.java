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

    private final PostService postService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllPosts() {
        List<PostDto> posts = postService.getAllPosts();
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


    @PatchMapping("/update")
    public ResponseEntity<?> updatePost(@RequestBody PostDto post) {
        boolean success = postService.updatePost(post);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{id}/delete")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        boolean  success = postService.deletePost(id);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
