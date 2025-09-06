package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.SubRedditDto;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubReddit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubRedditMapper {
    SubRedditDto toDto(SubReddit subReddit);
    SubReddit toEntity(SubRedditDto subRedditDto);
}
