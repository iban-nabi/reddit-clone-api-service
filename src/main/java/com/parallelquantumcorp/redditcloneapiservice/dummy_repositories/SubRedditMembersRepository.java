package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
