package com.jie.redditcloneapiservice.mappers;

import com.jie.redditcloneapiservice.dtos.response.PostResponse;
import com.jie.redditcloneapiservice.entities.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "subReddit.name", target = "subReddit")
    PostResponse toDto(Post post);
}
