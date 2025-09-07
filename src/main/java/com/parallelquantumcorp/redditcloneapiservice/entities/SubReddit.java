package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SubReddit {
    private Long id;
    private String name;
    private String description;
    private boolean archived;
}
