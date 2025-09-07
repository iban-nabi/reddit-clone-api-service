package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.SubRedditResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.SubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdateSubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.service.SubRedditService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{name}")
    public ResponseEntity<?> searchSubReddit(@PathVariable String query){
        List<SubRedditResponse> subReddits = subRedditService.searchSubReddits(query);
        return ResponseEntity.ok(subReddits);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubReddit(@RequestBody SubRedditRequest subRedditRequest){
        boolean success = subRedditService.createSubReddit(subRedditRequest);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update/{name}")
    public ResponseEntity<?> updateSubReddit(@PathVariable String name,
                                             @RequestBody UpdateSubRedditRequest updateSubRedditRequest){
        boolean success = subRedditService.updateSubReddit(name, updateSubRedditRequest);
        if(!success){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/delete/{name}")
    public ResponseEntity<?> deleteSubReddit(@PathVariable String name){
        boolean success = subRedditService.deleteSubReddit(name);
        if(!success){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }
}
