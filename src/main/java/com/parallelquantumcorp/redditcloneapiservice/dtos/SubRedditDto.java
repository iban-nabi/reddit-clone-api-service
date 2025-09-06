package com.parallelquantumcorp.redditcloneapiservice.dtos;

import lombok.Getter;
import lombok.Setter;
import org.mapstruct.Mapper;

@Setter
@Getter
public class SubRedditDto {
    private Long id;
    private String name;
    private String description;
}
