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

    public List<Post> getAllPosts(){
        return new ArrayList<>(posts.values());
    }

    public List<Post> searchPost(String query){
        return posts.values()
                .stream()
                .filter(post -> post.getTitle().toLowerCase().contains(query.toLowerCase())
                        && post.getContent().toLowerCase().contains(query.toLowerCase())
                        && !post.getUser().isArchived())
                .toList();

    }

    public List<Post> getAllSubRedditPosts(Long subredditId){
        return posts.values()
                .stream()
                .filter(post -> post.getSubreddit().getId()==subredditId
                        && post.getSubreddit().isArchived()
                        && !post.isArchived())
                .toList();
    }

    public void save(Post post){
        post.setId(posts.size()+1L);
        posts.put(post.getId(), post);
    }

    public boolean update(Post post){
        if(!posts.containsKey(post.getId())){
            return false;
        }
        posts.put(post.getId(), post);
        return true;
    }

    public boolean delete(Long id){
        if(!posts.containsKey(id)){
            return false;
        }
        posts.get(id).setArchived(true);
        return true;
    }
}
