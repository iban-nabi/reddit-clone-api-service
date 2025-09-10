package com.jie.redditcloneapiservice.controllers;

import com.jie.redditcloneapiservice.dtos.response.UserResponse;
import com.jie.redditcloneapiservice.service.SubRedditMembersService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling SubReddit membership operations.
 * This controller provides endpoints for managing subreddit memberships within a Reddit-like application.
 *
 * Endpoints include:
 * - Retrieving all members of a specific subreddit
 * - Joining a subreddit
 * - Leaving a subreddit
 *
 * Operations that involve joining, leaving, or fetching members may throw exceptions
 * if the requested subreddit does not exist or if the operation is invalid.
 *
 * @RestController Marks this class as a REST controller
 * @RequestMapping (/api/sub-reddit/{subRedditName}) Base path for membership-related endpoints
 */

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
