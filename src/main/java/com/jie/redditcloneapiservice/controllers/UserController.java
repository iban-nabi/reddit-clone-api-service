package com.jie.redditcloneapiservice.controllers;

import com.jie.redditcloneapiservice.dtos.request.ChangePasswordRequest;
import com.jie.redditcloneapiservice.dtos.response.UserResponse;
import com.jie.redditcloneapiservice.dtos.request.UserRequest;
import com.jie.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.jie.redditcloneapiservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST Controller for handling User-related operations.
 * This controller provides endpoints for managing user accounts within a Reddit-like application.
 *
 * Endpoints include:
 * - Retrieving a user's details by username
 * - Searching for users by query
 * - Registering a new user
 * - Updating a user's password
 * - Soft deleting a user account
 *
 * Operations that involve fetching, updating, or deleting users may throw
 * ResourceNotFoundException or IllegalStateException if the requested resource or operation is invalid.
 *
 * @RestController Marks this class as a REST controller
 * @RequestMapping("/api/user") Base path for all endpoints in this controller
 */

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            UserResponse user = userService.getUser(username);
            return ResponseEntity.ok(user);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchUsers(@PathVariable String query) {
        List<UserResponse> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try{
            userService.createUser(userRequest);
            return ResponseEntity.ok().build();

        }catch (IllegalStateException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest request){
        try{
            userService.updatePassword(request);
            return ResponseEntity.ok().build();

        }catch (Exception e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        try{
            userService.deleteUser();
            return ResponseEntity.ok().build();

        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}
