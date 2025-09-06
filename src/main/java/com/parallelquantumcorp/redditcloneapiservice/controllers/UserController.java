package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import com.parallelquantumcorp.redditcloneapiservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping("/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        User user = userRepository.findByUsername(username);
        if (user == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userMapper.toDtoResponse(user));
    }

    @GetMapping("/search/{username}")
    public ResponseEntity<?> searchUsers(@PathVariable String username) {
        List<User> users = userRepository.searchUsers(username);
        return ResponseEntity.ok(users.stream().map(userMapper::toDtoResponse).toList());
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        User user = userService.createUser(request);
        boolean success = userRepository.save(user);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    // to include encryption later on
    @PatchMapping("/update-password")
    public ResponseEntity<?> updatePassword(@RequestBody UserRequest request){
        boolean success = userRepository.updatePassword(request);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{username}/delete")
    public ResponseEntity<?> deleteUser(@PathVariable String username){
        boolean success = userRepository.delete(username);
        if(!success){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
