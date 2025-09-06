package com.parallelquantumcorp.redditcloneapiservice.dummy_repositories;

import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.mappers.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class CommentRepository {
    private final Map<Long, Comment> comments = new HashMap<>();
    private final CommentMapper commentMapper;

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

    public void save(CommentDto commentDto){
        commentDto.setId(comments.size()+1L);
        comments.put(commentDto.getId(), commentMapper.toEntity(commentDto));
    }

    public boolean update(CommentUpdateRequest comment){
        if(!comments.containsKey(comment.getId())){
            return false;
        }
        comments.get(comment.getId()).setContent(comment.getContent());
        return true;
    }

    public boolean delete(Long commentId){
        if(!comments.containsKey(commentId)){
            return false;
        }
        comments.get(commentId).setArchived(true);
        return true;
    }
}
