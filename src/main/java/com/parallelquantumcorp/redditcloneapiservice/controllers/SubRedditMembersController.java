package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.service.SubRedditMembersService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sub-reddit/{subRedditName}")
@AllArgsConstructor
public class SubRedditMembersController {
    private final SubRedditMembersService subRedditMembersService;

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@PathVariable String subRedditName){
        List<UserResponse> members = subRedditMembersService.getMembers(subRedditName);
        return ResponseEntity.ok(members);
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinSubReddit(@PathVariable String subRedditName, @RequestBody UserResponse userResponse){
        boolean success = subRedditMembersService.joinSubReddit(subRedditName, userResponse);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@PathVariable String subRedditName, @RequestBody UserResponse userResponse){
        boolean success = subRedditMembersService.leaveSubReddit(subRedditName, userResponse);
        if(!success){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }
}
