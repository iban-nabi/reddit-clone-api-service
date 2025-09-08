package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public abstract class Content {
    private User user;
    private long karma;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean archived;

    public void upvote(){
        karma++;
    }

    public void downvote(){
        karma--;
    }
}
