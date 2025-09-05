package com.parallelquantumcorp.redditcloneapiservice.entities;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;
    private LocalDate birthday;
    private boolean archived;
}
