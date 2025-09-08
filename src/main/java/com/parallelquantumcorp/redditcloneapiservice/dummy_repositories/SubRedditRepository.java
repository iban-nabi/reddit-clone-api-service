package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdateSubRedditRequest;
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
        subReddit.setId(subReddits.size()+1L);
        subReddits.put(subReddit.getName(), subReddit);
    }

    public void update(String name, UpdateSubRedditRequest updateSubRedditRequest) {
        subReddits.get(name).setDescription(updateSubRedditRequest.getDescription());
    }

    public void delete(String name) {
        subReddits.get(name).setArchived(true);
    }

    public boolean existsByName(String name) {
        return subReddits.containsKey(name);
    }
}
