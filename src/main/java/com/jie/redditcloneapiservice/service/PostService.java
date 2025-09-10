package com.jie.redditcloneapiservice.service;

import com.jie.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.jie.redditcloneapiservice.dtos.response.PostResponse;
import com.jie.redditcloneapiservice.dtos.request.PostRequest;
import com.jie.redditcloneapiservice.dtos.request.UpdatePostRequest;
import com.jie.redditcloneapiservice.dummy_repositories.PostRepository;
import com.jie.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.jie.redditcloneapiservice.dummy_repositories.UserRepository;
import com.jie.redditcloneapiservice.entities.Post;
import com.jie.redditcloneapiservice.entities.SubReddit;
import com.jie.redditcloneapiservice.entities.User;
import com.jie.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.jie.redditcloneapiservice.mappers.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    //repositories
    private final PostRepository postRepository;
    private final SubRedditRepository subRedditRepository;
    private final UserRepository userRepository;

    //mappers
    private final PostMapper postMapper;

    //helper
    private final AuthenticationContextHelper contextHelper;

    /**
     * Retrieves all non-archived posts from the repository.
     * 
     * This method fetches all posts, filters out archived ones, and converts them to DTOs.
     * 
     * @return List<PostResponse> A list of non-archived posts converted to PostResponse DTOs
     */
    public List<PostResponse> getAllPosts() {
        return postRepository.getAll()
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

    /**
     * Retrieves all non-archived posts from a specific subreddit.
     *
     * @param subRedditName The name of the subreddit to get posts from
     * @return A list of PostResponse objects containing the post information
     */
    public List<PostResponse> getAllSubRedditPosts(String subRedditName) {
        return postRepository.getAllSubRedditPosts(subRedditName)
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id The unique identifier of the post to retrieve
     * @return PostResponse A DTO containing the post information
     * @throws ResourceNotFoundException if the post doesn't exist or is archived
     */
    public PostResponse getPostById(Long id) throws ResourceNotFoundException{
        Post post = postRepository.getPost(id);
        if(post==null || post.isArchived()){
            throw new ResourceNotFoundException("Post does not exist");
        }
        return postMapper.toDto(post);
    }

    /**
     * Searches for posts based on the provided query and returns a list of matching non-archived posts.
     * 
     * @param query The search query string to filter posts
     * @return List<PostResponse> A list of post responses matching the search criteria, excluding archived posts
     */
    public List<PostResponse> searchPost(String query){
        return postRepository.searchPost(query)
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

    /**
     * Creates a new post in the system, optionally associated with a subreddit.
     * 
     * @param postRequest The request object containing post details (title, content, tag)
     * @param subRedditName The name of the subreddit where the post will be created (can be null for posts not in any subreddit)
     * @throws ResourceNotFoundException if the specified subreddit doesn't exist or is archived
     */
    public void createPost(PostRequest postRequest, String subRedditName) throws ResourceNotFoundException{
        SubReddit subReddit;
        User user = userRepository.findByUsername(contextHelper.getNameFromAuthToken());

        if(subRedditName == null){
            subReddit = null;

        }else{
            subReddit = subRedditRepository.getSubReddit(subRedditName);
            if (subReddit==null || subReddit.isArchived())
                throw new ResourceNotFoundException("Sub Reddit "+subRedditName+ "is archived");
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tag(postRequest.getTag())
                .subReddit(subReddit)
                .karma(0)
                .user(user)
                .createdAt(LocalDateTime.now())
                .archived(false)
                .build();

        postRepository.save(post);
    }

    /**
     * Updates an existing post with the provided information.
     * 
     * @param id The unique identifier of the post to update
     * @param updatePostRequest The request object containing the updated post information
     * @throws ResourceNotFoundException if the post is not found or if the post is archived
     */
    public void updatePost(Long id, UpdatePostRequest updatePostRequest) throws ResourceNotFoundException {
        Post post = postRepository.getPost(id);

        if (post == null) {
            throw new ResourceNotFoundException("Post with ID " + id + " not found");
        }

        if (post.isArchived()) {
            throw new ResourceNotFoundException("Cannot update archived post");
        }

        postRepository.update(id, updatePostRequest);
    }

    /**
     * Deletes a post with the specified ID.
     * 
     * @param id The ID of the post to be deleted
     * @throws ResourceNotFoundException if the post with given ID is not found or if the post is archived
     */
    public void deletePost(Long id) throws ResourceNotFoundException {
        Post post = postRepository.getPost(id);

        if (post == null) {
            throw new ResourceNotFoundException("Post with ID " + id + " not found");
        }

        if (post.isArchived()) {
            throw new ResourceNotFoundException("Cannot delete archived post");
        }

        postRepository.delete(id);
    }

    /**
     * Upvotes a post by its ID.
     * 
     * @param id The ID of the post to upvote
     * @throws ResourceNotFoundException if the post with given ID is not found or if the post is archived
     */
    public void upvotePost(Long id) throws ResourceNotFoundException {
        Post post = postRepository.getPost(id);

        if (post == null) {
            throw new ResourceNotFoundException("Post with ID " + id + " not found");
        }

        if (post.isArchived()) {
            throw new ResourceNotFoundException("Cannot upvote archived post");
        }

        postRepository.upvote(id);
    }

    /**
     * Decrements the vote count for a post with the specified ID.
     * 
     * @param id The unique identifier of the post to be downvoted
     * @throws ResourceNotFoundException if the post does not exist or is archived
     */
    public void downvotePost(Long id) throws ResourceNotFoundException {
        Post post = postRepository.getPost(id);

        if (post == null) {
            throw new ResourceNotFoundException("Post with ID " + id + " not found");
        }

        if (post.isArchived()) {
            throw new ResourceNotFoundException("Cannot downvote archived post");
        }

        postRepository.downvote(id);
    }
}
