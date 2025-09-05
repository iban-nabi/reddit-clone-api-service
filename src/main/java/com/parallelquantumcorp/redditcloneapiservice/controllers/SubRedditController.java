package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-reddit")
@AllArgsConstructor
public class SubRedditController {
    private final SubRedditRepository subRedditRepository;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubReddit(){
        return ResponseEntity.ok(subRedditRepository.getSubReddits());
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> searchSubReddit(@PathVariable String name){
        return ResponseEntity.ok(subRedditRepository.searchSubReddit(name));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubReddit(@RequestBody SubReddit subReddit){
        subRedditRepository.save(subReddit);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateSubReddit(@RequestBody SubReddit subReddit){
        subRedditRepository.update(subReddit);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{name}/delete")
    public ResponseEntity<?> deleteSubReddit(@PathVariable String name){
        subRedditRepository.delete(name);
        return ResponseEntity.ok().build();
    }

}
