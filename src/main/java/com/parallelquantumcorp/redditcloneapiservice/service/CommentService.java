package com.parallelquantumcorp.redditcloneapiservice.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.parallelquantumcorp.redditcloneapiservice.auth.AuthenticationContextHelper;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.UserRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import com.parallelquantumcorp.redditcloneapiservice.exceptions.ResourceNotFoundException;
import com.parallelquantumcorp.redditcloneapiservice.mappers.CommentMapper;

import lombok.AllArgsConstructor;

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


    /**
     * Retrieves all non-archived comments associated with a specific post.
     * 
     * @param postId The unique identifier of the post to retrieve comments from
     * @return List of CommentResponse objects containing comment details
     *         Empty list if no comments exist or all comments are archived
     */
    public List<CommentResponse> getAllCommentsFromPost(Long postId){
        return commentRepository.getCommentsFromPost(postId)
                .stream()
                .filter(comment -> !comment.isArchived())
                .map(commentMapper::toDto)
                .toList();
    }

    /**
     * Retrieves a specific non-archived comment by its ID.
     *
     * @param commentId The unique identifier of the comment to retrieve
     * @return CommentResponse object containing the comment details
     * @throws ResourceNotFoundException if the comment doesn't exist or is archived
     */
    public CommentResponse getComment(Long commentId) throws ResourceNotFoundException{
        Comment comment = commentRepository.getComment(commentId);
        if(comment == null || comment.isArchived()){
            throw new ResourceNotFoundException("Comment does not exist");
        }
        return commentMapper.toDto(comment);
    }


    /**
     * Creates a new comment for a given post. The comment can optionally be a reply to a parent comment.
     *
     * @param postId         the ID of the post to which the comment is being added
     * @param commentRequest the request object containing comment details such as content and optional parent comment ID
     * @throws ResourceNotFoundException if the specified post or parent comment does not exist
     */
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

    /**
     * Updates an existing comment with the provided update request.
     * This method retrieves the comment by its ID, checks if it exists and is not archived,
     * and then updates it using the provided CommentUpdateRequest
     *
     * @param id the ID of the comment to update
     * @param commentUpdateRequest the request containing updated comment data
     * @throws ResourceNotFoundException if the comment does not exist or is archived
     */
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

    /**
     * Deletes a comment by its ID.
     * This method retrieves the comment with the specified ID and deletes it if it exists and is not archived.
     * If the comment does not exist, or if it is archived, a ResourceNotFoundException is thrown.
     *
     * @param id the ID of the comment to delete
     * @throws ResourceNotFoundException if the comment does not exist or is archived
     */
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

    /**
     * Upvotes a comment by its ID.
     * 
     * @param id The ID of the comment to upvote
     * @throws ResourceNotFoundException if the comment is not found or if the comment is archived
     */
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

    /**
     * Decrements the vote count for a comment by one.
     *
     * @param id The unique identifier of the comment to downvote
     * @throws ResourceNotFoundException if the comment does not exist or if the comment is archived
     */
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
