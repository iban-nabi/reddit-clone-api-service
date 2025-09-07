package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.PostDto;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.PostMapper;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
    private final UserMapper userMapper;

    public List<PostDto> getAllPosts() {
        return postRepository.getAll()
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public PostDto getPostById(Long id){
        Post post = postRepository.getPost(id);
        if(post==null){
            return null;
        }
        return postMapper.toDto(post);
    }

    public List<PostDto> searchPost(String query){
        return postRepository.searchPost(query)
                .stream()
                .map(postMapper::toDto)
                .toList();
    }

    public void createPost(PostDto postResponse, String subRedditName) {
        SubReddit subReddit;
        User user = userRepository.findByUsername(postResponse.getUser().getUsername());

        if(subRedditName == null){
            subReddit = null;
        }else{
            subReddit = subRedditRepository.getSubReddit(subRedditName);
        }

        Post post = Post.builder()
                .title(postResponse.getTitle())
                .content(postResponse.getContent())
                .tag(postResponse.getTag())
                .subreddit(subReddit)
                .upvotes(0)
                .downvotes(0)
                .user(user)
                .createdAt(LocalDateTime.now())
                .archived(false)
                .build();

        postRepository.save(post);
    }

    public boolean updatePost(PostDto post){
        if(postRepository.existsById(post.getId())){
            Post updatedPost = postMapper.toEntity(post);
            postRepository.update(updatedPost);
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
