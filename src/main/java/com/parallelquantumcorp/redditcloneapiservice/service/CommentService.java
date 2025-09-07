package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    //repositories
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    //mappers
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;

    // to be updated : to include retrieving of mapped items
    public List<CommentResponse> getAllCommentsFromPost(Long postId){
        return commentRepository.getCommentsFromPost(postId)
                .stream()
                .filter(comment -> !comment.isArchived())
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentResponse getComment(Long commentId){
        Comment comment = commentRepository.getComment(commentId);
        if(comment == null || comment.isArchived()){
            return null;
        }
        return commentMapper.toDto(comment);
    }

    public boolean createComment(Long postId, CommentRequest commentRequest) {
        Post post = postRepository.getPost(postId);

        User user = userRepository.findByUsername(commentRequest.getUser().getUsername());

        if(post != null){
            Comment parent = (commentRequest.getParent()!=null) ?
                    commentRepository.getComment(commentRequest.getParent().getId()) : null;

            Comment comment = Comment.builder()
                    .post(post)
                    .content(commentRequest.getContent())
                    .user(user)
                    .parent(parent)
                    .createdAt(LocalDateTime.now())
                    .upvotes(0)
                    .downvotes(0)
                    .archived(false)
                    .build();

            commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public boolean updateComment(Long id, CommentUpdateRequest commentUpdateRequest) {
        if(commentRepository.existsById(id) &&
                !commentRepository.getComment(id).isArchived()){
            commentRepository.update(id, commentUpdateRequest);
            return true;
        }
        return false;
    }

    public boolean deleteComment(Long id) {
        if(commentRepository.existsById(id) &&
                !commentRepository.getComment(id).isArchived()){
            commentRepository.delete(id);
            return true;
        }
        return false;
    }
}
