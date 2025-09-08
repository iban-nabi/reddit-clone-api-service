package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubRedditMembersService {
    //repositories
    private final SubRedditMembersRepository subRedditMembersRepository;
    private final UserRepository userRepository;

    //mappers
    private final UserMapper userMapper;

    // helpers
    private final AuthenticationContextHelper contextHelper;

    public List<UserResponse> getMembers(String subRedditName){
        SubRedditMembers subRedditMembers = subRedditMembersRepository
                .getSubRedditMembers(subRedditName);

        return subRedditMembers.getMembers()
                .values()
                .stream()
                .filter(user -> !user.isArchived())
                .map(userMapper::toDtoResponse)
                .toList();
    }

    public void joinSubReddit(String subRedditName) throws ResourceNotFoundException {
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);

        if (user.isArchived()) {
            throw new IllegalStateException("Archived users cannot join subreddits");
        }

        if (!subRedditMembersRepository.subRedditExists(subRedditName)) {
            throw new ResourceNotFoundException("Subreddit '" + subRedditName + "' not found");
        }

        if (subRedditMembersRepository.userIsMember(subRedditName, username)) {
            throw new IllegalStateException("User is already a member of subreddit '" + subRedditName + "'");
        }

        subRedditMembersRepository.addMember(subRedditName, user);
    }

    public void leaveSubReddit(String subRedditName) throws ResourceNotFoundException {
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);

        if (user.isArchived()) {
            throw new IllegalStateException("Archived users cannot leave subreddits");
        }

        if (!subRedditMembersRepository.subRedditExists(subRedditName)) {
            throw new ResourceNotFoundException("Subreddit '" + subRedditName + "' not found");
        }

        if (!subRedditMembersRepository.userIsMember(subRedditName, username)) {
            throw new IllegalStateException("User is not a member of subreddit '" + subRedditName + "'");
        }

        subRedditMembersRepository.removeMember(subRedditName, user);
    }
}
