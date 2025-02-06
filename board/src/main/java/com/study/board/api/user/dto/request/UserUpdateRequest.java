package com.study.board.api.user.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank String username,
        @NotBlank String password
) {
}
