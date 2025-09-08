package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdatePostRequest;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PostRepository {
    private final Map<Long, Post> posts = new LinkedHashMap<>();

    public Post getPost(Long id){
        return posts.get(id);
    }

    public List<Post> getAll(){
        return new ArrayList<>(posts.values());
    }

    public List<Post> searchPost(String query){
        return posts.values()
                .stream()
                .filter(post -> post.getTitle().toLowerCase().contains(query.toLowerCase())
                        || post.getContent().toLowerCase().contains(query.toLowerCase())
                        && !post.getUser().isArchived())
                .toList();

    }

    public List<Post> getAllSubRedditPosts(String subRedditName){
        return posts.values()
                .stream()
                .filter(post -> post.getSubReddit() != null)
                .filter(post -> Objects.equals(post.getSubReddit().getName(), subRedditName)
                        && !post.getSubReddit().isArchived()
                        && !post.isArchived())
                .toList();
    }

    public void save(Post post){
        post.setId(posts.size()+1L);
        posts.put(post.getId(), post);
    }

    public boolean update(Long id, UpdatePostRequest updatePostRequest){
        posts.get(id).setContent(updatePostRequest.getContent());
        return true;
    }

    public boolean delete(Long id){
        posts.get(id).setArchived(true);
        return true;
    }

    public boolean existsById(Long id){
        return posts.containsKey(id);
    }

    public void upvote(Long id) {
        posts.get(id).upvote();
    }

    public void downvote(Long id) {
        posts.get(id).downvote();
    }
}
