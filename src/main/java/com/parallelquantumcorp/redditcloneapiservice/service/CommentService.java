package com.parallelquantumcorp.redditcloneapiservice.service;

import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentUpdateRequest;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.CommentRepository;
import com.parallelquantumcorp.redditcloneapiservice.dummy_repositories.PostRepository;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
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

    // to be updated : to include retrieving of mapped items
    public List<CommentDto> getAllCommentsFromPost(Long postId){
        return commentRepository.getCommentsFromPost(postId)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    public CommentDto getComment(Long commentId){
        Comment comment = commentRepository.getComment(commentId);
        if(comment == null){
            return null;
        }
        return commentMapper.toDto(comment);
    }

    public boolean createComment(CommentDto commentDto) {
        Post post = postRepository.getPost(commentDto.getPost().getId());

        if(post != null){
            Comment parent = (commentDto.getParent()!=null) ?
                    commentRepository.getComment(commentDto.getParent().getId()) : null;

            Comment comment = Comment.builder()
                    .post(post)
                    .content(commentDto.getContent())
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
        if(commentRepository.existsById(id)){
            commentRepository.update(id, commentUpdateRequest);
            return true;
        }
        return false;
    }

    public boolean deleteComment(Long commentId) {
        if(commentRepository.existsById(commentId)){
            commentRepository.delete(commentId);
            return true;
        }
        return false;
    }
}
