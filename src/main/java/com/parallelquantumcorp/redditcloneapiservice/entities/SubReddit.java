package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@AllArgsConstructor
public class SubReddit {
    private Long id;
    private String name;
    private String description;
    private Map<String, User> members = new HashMap<>();
    private boolean archived;
}
