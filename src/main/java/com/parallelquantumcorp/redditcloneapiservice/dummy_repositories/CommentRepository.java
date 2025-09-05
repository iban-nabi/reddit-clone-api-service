package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class CommentRepository {
    PostRepository postRepository;

    public List<Comment> getComments(Long postId){
        Post post = postRepository.getPost(postId);
        return new ArrayList<>(post.getComments().values());
    }

    public Comment getComment(Long postId, Long commentId){
        Post post = postRepository.getPost(postId);
        return post.getComments().get(commentId);
    }

    public void save(Long postId, Comment comment){
        Post post = postRepository.getPost(postId);
        Map<Long, Comment> comments = post.getComments();
        comment.setId(comments.size()+1L);
        comments.put(comment.getId(), comment);
    }

    public void update(Long postId, Comment comment){
        Post post = postRepository.getPost(postId);
        post.getComments().put(comment.getId(), comment);
    }

    public void delete(Long postId, Long commentId){
        Post post = postRepository.getPost(postId);
        post.getComments().get(commentId).setArchived(true);
    }
}
