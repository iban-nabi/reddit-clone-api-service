package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.PostDto;
import com.parallelquantumcorp.redditcloneapiservice.entities.Post;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    PostDto toDto(Post post);
    Post toEntity(PostDto postResponse);
}
