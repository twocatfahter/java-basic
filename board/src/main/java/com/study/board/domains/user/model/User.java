package com.study.board.domains.user.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class User {
    private String username;
    private String email;
    private String password;

    public void encodedPassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
