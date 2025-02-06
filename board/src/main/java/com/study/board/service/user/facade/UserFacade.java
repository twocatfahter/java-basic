package com.study.board.service.user.facade;

import com.study.board.api.user.dto.request.LoginRequest;
import com.study.board.api.user.dto.request.UserRequest;
import com.study.board.api.user.dto.request.UserUpdateRequest;
import com.study.board.api.user.dto.response.UserResponse;
import com.study.board.domains.user.model.User;
import com.study.board.domains.user.model.UserMapper;
import com.study.board.global.exception.DuplicateUserException;
import com.study.board.global.facade.BCryptPasswordEncoderFacade;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import com.study.board.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoderFacade bCryptPasswordEncoderFacade;

    @Transactional
    public Long createUser(UserRequest request) {
        User domain = userMapper.toDomain(request);
        String encodedPassword = bCryptPasswordEncoderFacade.encode(domain.getPassword());
        domain.encodedPassword(encodedPassword);

        validateDuplicate(domain.getEmail());
        return userService.createUser(domain);

    }

    private void validateDuplicate(String email) {
        userService.findByEmail(email).ifPresent(u -> {
            throw new DuplicateUserException("중복 이메일");
        });
    }

    public UserJpaEntity loginUser(@Valid LoginRequest request) {
        return userService.loginUser(request.email(), request.password());
    }

    public UserResponse getUserById(Long userId) {
        return UserResponse.fromEntity(userService.findUserById(userId));
    }

    public UserResponse updateUser(Long userId, @Valid UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    public void deleteUser(Long userId) {
        userService.deleteUser(userId);
    }
}
