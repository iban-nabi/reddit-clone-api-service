package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.CommentResponse;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "parent.id", target = "parentCommentId")
    CommentResponse toDto(Comment comment);
}
