package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.PostResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.PostRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.UpdatePostRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
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

    public List<PostResponse> getAllPosts() {
        return postRepository.getAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public List<PostResponse> getAllSubRedditPosts(String subRedditName) {
        return postRepository.getAllSubRedditPosts(subRedditName)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostResponse getPostById(Long id){
        Post post = postRepository.getPost(id);
        if(post==null){
            return null;
        }
        return postMapper.toDto(post);
    }

    public List<PostResponse> searchPost(String query){
        return postRepository.searchPost(query)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public void createPost(PostRequest postRequest, String subRedditName) {
        SubReddit subReddit;
        User user = userRepository.findByUsername(postRequest.getUser().getUsername());

        if(subRedditName == null){
            subReddit = null;
        }else{
            subReddit = subRedditRepository.getSubReddit(subRedditName);
        }

        Post post = Post.builder()
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .tag(postRequest.getTag())
                .subreddit(subReddit)
                .upvotes(0)
                .downvotes(0)
                .user(user)
                .createdAt(LocalDateTime.now())
                .archived(false)
                .build();

        postRepository.save(post);
    }

    public boolean updatePost(Long id, UpdatePostRequest updatePostRequest){
        if(postRepository.existsById(id)){
            postRepository.update(id, updatePostRequest);
            return true;
        }
        return false;
    }

    public boolean deletePost(Long id){
        if(postRepository.existsById(id)){
            postRepository.delete(id);
            return true;
        }
        return false;
    }
}
