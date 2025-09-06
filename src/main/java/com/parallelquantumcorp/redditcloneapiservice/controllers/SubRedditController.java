package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.SubRedditDto;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.mappers.SubRedditMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-reddit")
@AllArgsConstructor
public class SubRedditController {
    private final SubRedditRepository subRedditRepository;
    private final SubRedditMapper subRedditMapper;

    @GetMapping("/all")
    public ResponseEntity<?> getAllSubReddit(){
        List<SubRedditDto> subReddits = subRedditRepository.getSubReddits()
                .stream()
                .map(subRedditMapper::toDto)
                .toList();
        return ResponseEntity.ok(subReddits);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> searchSubReddit(@PathVariable String name){
        List<SubRedditDto> subReddits = subRedditRepository.searchSubReddit(name)
                .stream()
                .map(subRedditMapper::toDto)
                .toList();
        return ResponseEntity.ok(subReddits);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubReddit(@RequestBody SubRedditDto subRedditDto){
        subRedditRepository.save(subRedditMapper.toEntity(subRedditDto));
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateSubReddit(@RequestBody SubReddit subReddit){
        boolean success = subRedditRepository.update(subReddit);
        if(!success){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{name}/delete")
    public ResponseEntity<?> deleteSubReddit(@PathVariable String name){
        boolean success = subRedditRepository.delete(name);
        if(!success){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().build();
    }

}
