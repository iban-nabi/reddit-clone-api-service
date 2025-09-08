package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.PostResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.PostRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdatePostRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.mappers.PostMapper;
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

    public List<PostResponse> getAllPosts() {
        return postRepository.getAll()
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostResponse> getAllSubRedditPosts(String subRedditName) {
        return postRepository.getAllSubRedditPosts(subRedditName)
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

    public PostResponse getPostById(Long id) throws ResourceNotFoundException{
        Post post = postRepository.getPost(id);
        if(post==null || post.isArchived()){
            throw new ResourceNotFoundException("Post does not exist");
        }
        return postMapper.toDto(post);
    }

    public List<PostResponse> searchPost(String query){
        return postRepository.searchPost(query)
                .stream()
                .filter(post -> !post.isArchived())
                .map(postMapper::toDto)
                .toList();
    }

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
