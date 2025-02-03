package com.study.board.service.user.dto;

import com.study.board.domains.user.model.User;

public record UserServiceRequest(
        String username,
        String encodedPassword,
        String email
) {

    public static UserServiceRequest toService(User user) {
        return new UserServiceRequest(
                user.getUsername(),
                user.getPassword(),
                user.getEmail()
        );
    }
}
