package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void update(Comment comment){
        comments.put(comment.getId(), comment);
    }

    public void delete(Long commentId){
        comments.get(commentId).setArchived(true);
    }
}
