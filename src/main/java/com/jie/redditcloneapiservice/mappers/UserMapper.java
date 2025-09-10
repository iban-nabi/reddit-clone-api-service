package com.jie.redditcloneapiservice.mappers;

import com.jie.redditcloneapiservice.dtos.response.UserResponse;
import com.jie.redditcloneapiservice.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toDtoResponse(User user);
}
