package com.study.board.service.user;

import com.study.board.domains.user.model.User;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import com.study.board.infrastructure.user.repository.UserJpaRepository;
import com.study.board.service.user.dto.UserServiceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;

    @Transactional
    public Long createUser(User domain) {
        UserServiceRequest request = UserServiceRequest.toService(domain);
        UserJpaEntity entity = UserJpaEntity.toEntity(request);
        return userJpaRepository.save(entity).getId();
    }

    public Optional<UserJpaEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }
}
