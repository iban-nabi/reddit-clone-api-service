package com.jie.redditcloneapiservice.mappers;

import com.jie.redditcloneapiservice.dtos.response.SubRedditResponse;
import com.jie.redditcloneapiservice.entities.SubReddit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    SubRedditResponse toDto(SubReddit subReddit);
}
