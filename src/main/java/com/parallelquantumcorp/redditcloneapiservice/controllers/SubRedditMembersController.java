package com.parallelquantumcorp.redditcloneapiservice.controllers;

import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sub-reddit/{subRedditId}")
@AllArgsConstructor
public class SubRedditMembersController {
    private final SubRedditMembersRepository subRedditMembersRepository;

    @PostMapping("/join")
    public void join(@PathVariable Long subRedditId, @RequestBody User user){
        subRedditMembersRepository.addMember(subRedditId, user);
    }

    @PostMapping("/leave")
    public void leave(@PathVariable Long subRedditId, @RequestBody User user){
        subRedditMembersRepository.removeMember(subRedditId, user);
    }
}
