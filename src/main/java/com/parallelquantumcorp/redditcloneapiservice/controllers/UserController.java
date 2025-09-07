package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.ChangePasswordRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UserDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        UserDto user = userService.getUser(username);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUsers(@PathVariable String query) {
        List<UserDto> users = userService.searchUsers(query);
        return ResponseEntity.ok(users);
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        boolean success = userService.createUser(userRequest);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // to include encryption later on
    @PatchMapping("/{username}/update-password")
    public ResponseEntity<?> updatePassword(@PathVariable String username, @RequestBody ChangePasswordRequest request){
        boolean success = userService.updatePassword(username, request);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        boolean success = userService.deleteUser(username);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
