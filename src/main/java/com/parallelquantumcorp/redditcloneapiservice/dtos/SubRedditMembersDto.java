package com.parallelquantumcorp.redditcloneapiservice.dtos;

import java.util.Map;

public class SubRedditMembersDto {
    private SubRedditDto subReddit;
    private Map<String, UserDto> members;
}
