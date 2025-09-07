package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.SubRedditDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UpdateSubRedditRequest;
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
        List<SubRedditDto> subReddits = subRedditService.getAllSubReddits();
        return ResponseEntity.ok(subReddits);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> searchSubReddit(@PathVariable String query){
        List<SubRedditDto> subReddits = subRedditService.searchSubReddits(query);
        return ResponseEntity.ok(subReddits);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubReddit(@RequestBody SubRedditDto subRedditDto){
        boolean success = subRedditService.createSubReddit(subRedditDto);
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
