package com.study.board.service.user;

import com.study.board.api.user.dto.request.UserUpdateRequest;
import com.study.board.api.user.dto.response.UserResponse;
import com.study.board.domains.user.model.User;
import com.study.board.infrastructure.user.entity.UserJpaEntity;
import com.study.board.infrastructure.user.repository.UserJpaRepository;
import com.study.board.service.user.dto.UserServiceRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserJpaRepository userJpaRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long createUser(User domain) {
        UserServiceRequest request = UserServiceRequest.toService(domain);
        UserJpaEntity entity = UserJpaEntity.toEntity(request);
        return userJpaRepository.save(entity).getId();
    }

    public Optional<UserJpaEntity> findByEmail(String email) {
        return userJpaRepository.findByEmail(email);
    }

    public UserJpaEntity loginUser(@NotBlank String email, @NotBlank String password) {
        UserJpaEntity userEntity = userJpaRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User Not found"));

        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        if (userEntity.isDeleted()) {
            throw new IllegalStateException("Deleted User");
        }

        return userEntity;
    }

    public UserJpaEntity findUserById(Long userId) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not Found"));

        if (user.isDeleted()) {
            throw new IllegalStateException("Deleted User");
        }

        return user;
    }

    @Transactional
    public UserResponse updateUser(Long userId, @Valid UserUpdateRequest request) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not Found"));

        if (request.username() != null || !request.username().isEmpty()) {
            user.updateUsername(request.username());
        }

        if (request.password() != null || !request.password().isEmpty()) {
            user.updatePassword(passwordEncoder.encode(request.password()));
        }

        return UserResponse.fromEntity(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        UserJpaEntity user = userJpaRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not Found"));

        user.delete();
    }
}
