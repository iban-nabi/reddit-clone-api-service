package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SubRedditMembersRepository {
    //<Sub Reddit ID, SubRedditMembers object>
    private final Map<Long, SubRedditMembers> subRedditMembersMap = new HashMap<>();

    public void createSubRedditMembers(SubReddit subReddit) {
        SubRedditMembers subRedditMembers = SubRedditMembers.builder()
                .members(new HashMap<>())
                .subReddit(subReddit)
                .build();
        subRedditMembersMap.put(subReddit.getId(), subRedditMembers);
    }

    public void addMember(Long subRedditId, User user){
        subRedditMembersMap.get(subRedditId).getMembers().put(user.getUsername(), user);
    }

    public void removeMember(Long subRedditId, User user){
        subRedditMembersMap.get(subRedditId).getMembers().remove(user.getUsername());
    }
}
