package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.SubRedditMembersDto;
import com.parallelquantumcorp.redditcloneapiservice.entities.SubRedditMembers;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubRedditMembersMapper {
    SubRedditMembersDto toDto(SubRedditMembers subRedditMembers);
    SubRedditMembers toEntity(SubRedditMembersDto subRedditMembersDto);
}
