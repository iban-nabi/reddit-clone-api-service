package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.PostResponse;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.SubRedditRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class PostService {
    private final SubRedditRepository subRedditRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Post createPost(PostResponse postResponse, String subRedditName) {
        SubReddit subReddit;
        User user = userRepository.findByUsername(postResponse.getUser().getUsername());
        if(subRedditName == null){
            subReddit = null;
        }else{
            subReddit = subRedditRepository.getSubReddit(subRedditName);
        }

        return Post.builder()
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
    }
}
