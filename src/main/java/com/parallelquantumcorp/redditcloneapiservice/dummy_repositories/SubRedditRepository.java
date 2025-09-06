package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class SubRedditRepository {
    private final SubRedditMembersRepository subRedditMembersRepository;
    private final Map<String, SubReddit> subReddits = new HashMap<>();

    public List<SubReddit> getSubReddits() {
        return new ArrayList<>(subReddits.values());
    }

    public List<SubReddit> searchSubReddit(String query) {
        return subReddits.values()
                .stream()
                .filter(subReddit -> subReddit.getName().toLowerCase().contains(query.toLowerCase())
                                    && !subReddit.isArchived())
                .toList();
    }

    public SubReddit getSubReddit(String subRedditName) {
        return subReddits.get(subRedditName);
    }

    public void save(SubReddit subReddit) {
        subReddits.put(subReddit.getName(), subReddit);
        subRedditMembersRepository.createSubRedditMembers(subReddit);
    }

    public boolean update(SubReddit subReddit) {
        if(!subReddits.containsKey(subReddit.getName())) {
            return false;
        }
        subReddits.put(subReddit.getName(), subReddit);
        return true;
    }

    public boolean delete(String name) {
        if(!subReddits.containsKey(name)) {
            return false;
        }
        subReddits.get(name).setArchived(true);
        return true;
    }
}
