package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
public class SubRedditMembers {
    private SubReddit subReddit;
    private Map<String, User> members;
}
