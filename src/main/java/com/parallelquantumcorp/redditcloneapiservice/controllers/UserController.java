package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.ChangePasswordRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserResponse user = userService.getUser(username);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchUsers(@PathVariable String query) {
        List<UserResponse> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        boolean success = userService.createUser(userRequest);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // to include encryption later on
    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest request){
        boolean success = userService.updatePassword(request);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        boolean success = userService.deleteUser();
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
