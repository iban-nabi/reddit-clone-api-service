package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.ChangePasswordRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.PostResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
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
        try {
            UserResponse user = userService.getUser(username);
            return ResponseEntity.ok(user);

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
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
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest request){
        try{
            userService.updatePassword(request);
            return ResponseEntity.ok().build();

        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().build();

        }
    }

    @PatchMapping("/delete")
    public ResponseEntity<?> deleteUser(){
        try{
            userService.deleteUser();
            return ResponseEntity.ok().build();

        }catch (ResourceNotFoundException e){
            return ResponseEntity.badRequest().build();

        }
    }
}
