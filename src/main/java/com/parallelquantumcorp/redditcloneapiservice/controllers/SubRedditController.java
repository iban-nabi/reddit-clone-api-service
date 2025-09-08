package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.SubRedditResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.SubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdateSubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling SubReddit-related operations.
 * This controller provides endpoints for managing subreddits within a Reddit-like application.
 *
 * Endpoints include:
 * - Retrieving all subreddits
 * - Searching for subreddits by query
 * - Creating new subreddits
 * - Updating existing subreddits
 * - Soft deleting subreddits
 *
 * Operations that involve accessing specific subreddits may throw
 * ResourceNotFoundException if the requested resource does not exist.
 *
 * @RestController Marks this class as a REST controller
 * @RequestMapping("/api/sub-reddit") Base path for all endpoints in this controller
 */

@RestController
@RequestMapping("/api/sub-reddit")
@AllArgsConstructor
public class SubRedditController {
    //services
    private final SubRedditService subRedditService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubReddits(){
        List<SubRedditResponse> subReddits = subRedditService.getAllSubReddits();
        return ResponseEntity.ok(subReddits);
    }

    @GetMapping("/{query}")
    public ResponseEntity<?> searchSubReddit(@PathVariable String query){
        List<SubRedditResponse> subReddits = subRedditService.searchSubReddits(query);
        return ResponseEntity.ok(subReddits);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubReddit(@RequestBody SubRedditRequest subRedditRequest){
        try{
            subRedditService.createSubReddit(subRedditRequest);
            return ResponseEntity.ok().build();

        }catch(IllegalStateException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PatchMapping("/update/{name}")
    public ResponseEntity<?> updateSubReddit(@PathVariable String name,
                                             @RequestBody UpdateSubRedditRequest updateSubRedditRequest){
        try{
            subRedditService.updateSubReddit(name, updateSubRedditRequest);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            System.out.println("sadly i am here");
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PatchMapping("/delete/{name}")
    public ResponseEntity<?> deleteSubReddit(@PathVariable String name){
        try{
            subRedditService.deleteSubReddit(name);
            return ResponseEntity.ok().build();

        }catch(ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }
}
