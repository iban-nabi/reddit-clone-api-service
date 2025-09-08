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

    /**
     * Retrieves a list of active (non-archived) members for a specified subreddit.
     *
     * @param subRedditName The name of the subreddit to get members from
     * @return List<UserResponse> A list of user responses containing active member information
     * @throws ResourceNotFoundException if the specified subreddit does not exist
     */
    public List<UserResponse> getMembers(String subRedditName) throws ResourceNotFoundException {
        SubRedditMembers subRedditMembers = subRedditMembersRepository
                .getSubRedditMembers(subRedditName);

        if(subRedditMembers == null){
            throw new ResourceNotFoundException("Sub Reddit does not exist");
        }

        return subRedditMembers.getMembers()
                .values()
                .stream()
                .filter(user -> !user.isArchived())
                .map(userMapper::toDtoResponse)
                .toList();
    }

    /**
     * Adds the authenticated user as a member to the specified subreddit.
     *
     * @param subRedditName the name of the subreddit to join
     * @throws ResourceNotFoundException if the specified subreddit does not exist
     * @throws IllegalStateException if the user is archived or is already a member of the subreddit
     */
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

    /**
     * Removes a user from a specified subreddit.
     * 
     * @param subRedditName The name of the subreddit to leave
     * @throws ResourceNotFoundException if the specified subreddit does not exist
     * @throws IllegalStateException if the user is archived or is not a member of the subreddit
     */
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
