package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
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

    // helpers
    private final AuthenticationContextHelper contextHelper;

    // to be updated : to include retrieving of mapped items
    public List<CommentResponse> getAllCommentsFromPost(Long postId){
        return commentRepository.getCommentsFromPost(postId)
                .stream()
                .filter(comment -> !comment.isArchived())
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentResponse getComment(Long commentId) throws ResourceNotFoundException{
        Comment comment = commentRepository.getComment(commentId);
        if(comment == null || comment.isArchived()){
            throw new ResourceNotFoundException("Comment does not exist");
        }
        return commentMapper.toDto(comment);
    }

    public void createComment(Long postId, CommentRequest commentRequest) throws ResourceNotFoundException {
        Post post = postRepository.getPost(postId);
        User user = userRepository.findByUsername(contextHelper.getNameFromAuthToken());

        if (post==null)
            throw new ResourceNotFoundException("Post Id " + postId + " does not exist");

        Comment parent = (commentRequest.getParentCommentId()!=null) ?
                commentRepository.getComment(commentRequest.getParentCommentId()) : null;

        if(commentRequest.getParentCommentId()!=null && parent == null) {
            throw new ResourceNotFoundException("Parent Comment Id " + commentRequest.getParentCommentId()
                    + " does not exist");
        }

        Comment comment = Comment.builder()
                .post(post)
                .content(commentRequest.getContent())
                .user(user)
                .parent(parent)
                .createdAt(LocalDateTime.now())
                .karma(0)
                .archived(false)
                .build();

        commentRepository.save(comment);
    }

    public void updateComment(Long id, CommentUpdateRequest commentUpdateRequest) throws ResourceNotFoundException{
        Comment comment = commentRepository.getComment(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }

        if (comment.isArchived()) {
            throw new ResourceNotFoundException("Cannot update archived comment");
        }

        commentRepository.update(id, commentUpdateRequest);
    }

    public void deleteComment(Long id) throws ResourceNotFoundException {
        Comment comment = commentRepository.getComment(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }

        if (comment.isArchived()) {
            throw new ResourceNotFoundException("Cannot delete archived comment");
        }

        commentRepository.delete(id);
    }

    public void upvoteComment(Long id) throws ResourceNotFoundException {
        Comment comment = commentRepository.getComment(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }

        if (comment.isArchived()) {
            throw new ResourceNotFoundException("Cannot upvote archived comment");
        }

        commentRepository.upvote(id);
    }

    public void downvoteComment(Long id) throws ResourceNotFoundException {
        Comment comment = commentRepository.getComment(id);

        if (comment == null) {
            throw new ResourceNotFoundException("Comment with ID " + id + " not found");
        }

        if (comment.isArchived()) {
            throw new ResourceNotFoundException("Cannot downvote archived comment");
        }

        commentRepository.downvote(id);
    }
}
