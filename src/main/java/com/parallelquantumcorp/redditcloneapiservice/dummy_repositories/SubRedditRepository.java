package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
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

    public void save(SubReddit subReddit) {
        subReddits.put(subReddit.getName(), subReddit);
    }

    public void update(SubReddit subReddit) {
        subReddits.put(subReddit.getName(), subReddit);
    }

    public void delete(String name) {
        subReddits.get(name).setArchived(true);
    }
}
