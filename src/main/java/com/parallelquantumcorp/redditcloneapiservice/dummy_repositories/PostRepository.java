package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostRepository {
    private final Map<Long, Post> posts = new LinkedHashMap<>();

    public Post getPost(Long id){
        return posts.get(id);
    }

    public List<Post> getPosts(){
        return new ArrayList<>(posts.values());
    }

    public void save(Post post){
        post.setId(posts.size()+1L);
        posts.put(post.getId(), post);
    }

    public void update(Post post){
        posts.put(post.getId(), post);
    }

    public void delete(Long id){
        posts.get(id).setArchived(true);
    }
}
