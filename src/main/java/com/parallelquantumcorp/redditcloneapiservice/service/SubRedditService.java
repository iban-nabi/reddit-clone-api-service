package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.SubRedditResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.SubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdateSubRedditRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditMembersRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.mappers.SubRedditMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SubRedditService {
    //repositories
    private final SubRedditRepository subRedditRepository;
    private final SubRedditMembersRepository subRedditMembersRepository;

    //mappers
    private final SubRedditMapper subRedditMapper;
    private final UserRepository userRepository;

    //helpers
    private final AuthenticationContextHelper contextHelper;

    /**
     * Retrieves all non-archived subreddits from the repository.
     * 
     * This method fetches all subreddits, filters out the archived ones,
     * and maps them to DTOs using the subRedditMapper.
     * 
     * @return List<SubRedditResponse> A list of subreddit DTOs containing only active subreddits
     */
    public List<SubRedditResponse> getAllSubReddits(){
        return subRedditRepository.getSubReddits()
                .stream()
                .filter(subReddit -> !subReddit.isArchived())
                .map(subRedditMapper::toDto)
                .toList();
    }

    /**
     * Searches for active SubReddits matching the given query string.
     *
     * @param query The search query string to match against SubReddits
     * @return List of SubRedditResponse containing the matching non-archived SubReddits
     */
    public List<SubRedditResponse> searchSubReddits(String query){
        return subRedditRepository.searchSubReddit(query)
                .stream()
                .filter(subReddit -> !subReddit.isArchived())
                .map(subRedditMapper::toDto)
                .toList();
    }

    /**
     * Creates a new subreddit with the given details.
     * 
     * @param subRedditRequest The request containing subreddit details (name and description)
     * @throws IllegalStateException if a subreddit with the same name already exists
     */
    public void createSubReddit(SubRedditRequest subRedditRequest) throws IllegalStateException {
        if (subRedditRepository.existsByName(subRedditRequest.getName())) {
            throw new IllegalStateException("Subreddit '" + subRedditRequest.getName() + "' already exists");
        }

        String username = contextHelper.getNameFromAuthToken();
        User user = userRepository.findByUsername(username);

        SubReddit subReddit = SubReddit.builder()
                .name(subRedditRequest.getName())
                .description(subRedditRequest.getDescription())
                .archived(false)
                .build();

        subRedditRepository.save(subReddit);
        subRedditMembersRepository.createSubRedditMembers(subReddit);
        subRedditMembersRepository.addMember(subReddit.getName(), user);
    }

    /**
     * Updates an existing subreddit with the specified details.
     *
     * @param name The name of the subreddit to update
     * @param updateSubRedditRequest The request object containing the updated subreddit information
     * @throws ResourceNotFoundException if the subreddit does not exist or is archived
     */
    public void updateSubReddit(String name, UpdateSubRedditRequest updateSubRedditRequest)
            throws ResourceNotFoundException {
        SubReddit subReddit = subRedditRepository.getSubReddit(name);

        if (subReddit == null) {
            throw new ResourceNotFoundException("Subreddit '" + name + "' not found");
        }

        if (subReddit.isArchived()) {
            throw new ResourceNotFoundException("Cannot update archived subreddit");
        }

        subRedditRepository.update(name, updateSubRedditRequest);
    }

    /**
     * Deletes a subreddit by its name if it exists and is not archived.
     * 
     * @param name The name of the subreddit to delete
     * @throws ResourceNotFoundException if the subreddit doesn't exist or is archived
     */
    public void deleteSubReddit(String name) throws ResourceNotFoundException {
        SubReddit subReddit = subRedditRepository.getSubReddit(name);

        if (subReddit == null) {
            throw new ResourceNotFoundException("Subreddit '" + name + "' not found");
        }

        if (subReddit.isArchived()) {
            throw new ResourceNotFoundException("Cannot delete archived subreddit");
        }

        subRedditRepository.delete(name);
    }
}
