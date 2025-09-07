package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.response.SubRedditResponse;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    SubRedditResponse toDto(SubReddit subReddit);
    SubReddit toEntity(SubRedditResponse subRedditResponse);
}
