package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.UserDto;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sub-reddit/{subRedditName}")
@AllArgsConstructor
public class SubRedditMembersController {
    private final SubRedditMembersRepository subRedditMembersRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @GetMapping("/members")
    public ResponseEntity<?> getMembers(@PathVariable String subRedditName){
        SubRedditMembers subRedditMembers = subRedditMembersRepository
                .getSubRedditMembers(subRedditName);
        List<UserDto> members = subRedditMembers.getMembers()
                .values()
                .stream()
                .map(userMapper::toDtoResponse)
                .toList();
        return ResponseEntity.ok(members);
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@PathVariable String subRedditName, @RequestBody UserDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        subRedditMembersRepository.addMember(subRedditName, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/leave")
    public ResponseEntity<?> leave(@PathVariable String subRedditName, @RequestBody UserDto userDto){
        User user = userRepository.findByUsername(userDto.getUsername());
        if(user==null){
            return ResponseEntity.notFound().build();
        }
        subRedditMembersRepository.removeMember(subRedditName, user);
        return ResponseEntity.ok().build();
    }
}
