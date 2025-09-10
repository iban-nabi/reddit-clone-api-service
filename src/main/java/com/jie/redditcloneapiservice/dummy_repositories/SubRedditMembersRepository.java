package com.jie.redditcloneapiservice.dummy_repositories;

import com.jie.redditcloneapiservice.entities.SubReddit;
import com.jie.redditcloneapiservice.entities.SubRedditMembers;
import com.jie.redditcloneapiservice.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Repository class for managing subreddit members and their relationships.
 * This class handles the storage and operations related to subreddit membership.
 * 
 * The class maintains an in-memory map of subreddit names to SubRedditMembers objects,
 * allowing for efficient member management operations.
 */
@Component
public class SubRedditMembersRepository {
    //<Sub Reddit Name, SubRedditMembers object>
    private final Map<String, SubRedditMembers> subRedditMembersMap = new HashMap<>();

    public void createSubRedditMembers(SubReddit subReddit) {
        SubRedditMembers subRedditMembers = SubRedditMembers.builder()
                .members(new HashMap<>())
                .subReddit(subReddit)
                .build();
        subRedditMembersMap.put(subReddit.getName(), subRedditMembers);
    }

    public SubRedditMembers getSubRedditMembers(String subRedditName) {
        return subRedditMembersMap.get(subRedditName);
    }

    public void addMember(String subRedditName, User user){
        subRedditMembersMap.get(subRedditName).getMembers().put(user.getUsername(), user);
    }

    public void removeMember(String subRedditName, User user){
        subRedditMembersMap.get(subRedditName).getMembers().remove(user.getUsername());
    }

    public boolean userIsMember(String subRedditName, String username){
        return subRedditMembersMap.get(subRedditName).getMembers().containsKey(username);
    }

    public boolean subRedditExists(String subRedditName){
        return subRedditMembersMap.containsKey(subRedditName);
    }
}
