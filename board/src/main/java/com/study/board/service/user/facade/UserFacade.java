package com.study.board.service.user.facade;

import com.study.board.api.user.dto.request.UserRequest;
import com.study.board.domains.user.model.User;
import com.study.board.domains.user.model.UserMapper;
import com.study.board.global.exception.DuplicateUserException;
import com.study.board.global.facade.BCryptPasswordEncoderFacade;
import com.study.board.service.user.UserService;
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
}
