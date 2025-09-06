package com.parallelquantumcorp.redditcloneapiservice.mappers;

import com.parallelquantumcorp.redditcloneapiservice.dtos.UserRequest;
import com.parallelquantumcorp.redditcloneapiservice.dtos.UserDto;
import com.parallelquantumcorp.redditcloneapiservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserRequest toDtoRequest(User user);
    UserDto toDtoResponse(User user);
    User toEntity(UserRequest userRequest);
    User toEntity(UserDto userDto);
}
