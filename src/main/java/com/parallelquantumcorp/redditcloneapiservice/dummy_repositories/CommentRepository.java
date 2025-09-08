package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.dtos.request.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository class for managing Comment entities in memory using a HashMap.
 * This class provides CRUD operations and additional functionalities for Comment management.
 */
@Component
@AllArgsConstructor
public class CommentRepository {
    private final Map<Long, Comment> comments = new HashMap<>();

    public List<Comment> getCommentsFromPost(Long postId){
        return comments.values()
                .stream()
                .filter(comment -> comment.getPost().getId().equals(postId)
                        && !comment.getPost().isArchived()
                        && !comment.isArchived())
                .toList();
    }

    public Comment getComment(Long commentId){
        return comments.get(commentId);
    }

    public void save(Comment comment){
        comment.setId(comments.size()+1L);
        comments.put(comment.getId(), comment);
    }

    public void update(Long id, CommentUpdateRequest comment){
        comments.get(id).setContent(comment.getContent());
        comments.get(id).setUpdatedAt(LocalDateTime.now());
    }

    public void delete(Long commentId){
        comments.get(commentId).setArchived(true);
    }

    public void upvote(Long id) {
        comments.get(id).upvote();
    }

    public void downvote(Long id) {
        comments.get(id).downvote();
    }
}
