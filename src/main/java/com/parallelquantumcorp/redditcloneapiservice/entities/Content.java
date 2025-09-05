package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class Content {
    private User user;
    private int upvotes;
    private int downvotes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean archived;

    public void upvote(){
        upvotes++;
    }

    public void downvote(){
        downvotes++;
    }
}
