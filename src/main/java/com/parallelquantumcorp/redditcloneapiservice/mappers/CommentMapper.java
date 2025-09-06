package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.CommentDto;
import com.parallelquantumcorp.redditcloneapiservice.entities.Comment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentDto toDto(Comment comment);
    Comment toEntity(CommentDto commentDto);
}
