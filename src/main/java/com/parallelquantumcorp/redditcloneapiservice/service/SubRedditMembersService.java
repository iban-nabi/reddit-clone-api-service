package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.UserResponse;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
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

    public boolean joinSubReddit(String subRedditName){
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);
        if(user!=null && !user.isArchived()
                && subRedditMembersRepository.subRedditExists(subRedditName)
                && !subRedditMembersRepository.userIsMember(subRedditName, username)){
            subRedditMembersRepository.addMember(subRedditName, user);
            return true;
        }
        return false;
    }

    public boolean leaveSubReddit(String subRedditName){
        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);
        if(user!=null && !user.isArchived()
                && subRedditMembersRepository.subRedditExists(subRedditName)
                && subRedditMembersRepository.userIsMember(subRedditName, username)){
            subRedditMembersRepository.removeMember(subRedditName, user);
            return true;
        }
        return false;
    }
}
