package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.service.SubRedditMembersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sub-reddit/{subRedditName}")
@AllArgsConstructor
public class SubRedditMembersController {
    private final SubRedditMembersService subRedditMembersService;

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@PathVariable String subRedditName){
        try{
            List<UserResponse> members = subRedditMembersService.getMembers(subRedditName);
            return ResponseEntity.ok(members);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/join")
    public ResponseEntity<?> joinSubReddit(@PathVariable String subRedditName){
        try{
            subRedditMembersService.joinSubReddit(subRedditName);
            return ResponseEntity.ok().build();

        }catch(Exception e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@PathVariable String subRedditName){
        try{
            subRedditMembersService.leaveSubReddit(subRedditName);
            return ResponseEntity.ok().build();

        }catch(Exception e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));

        }
    }
}
