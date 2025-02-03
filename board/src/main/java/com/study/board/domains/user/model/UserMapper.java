package com.study.board.domains.user.model;

import com.study.board.api.user.dto.request.UserRequest;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // controller dto -> domain
    public User toDomain(UserRequest request) {
        return User.builder()
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .build();
    }

    // entity -> domain
    public User entityToDomain(UserJpaEntity entity) {
        return User.builder()
                .username(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }
}
