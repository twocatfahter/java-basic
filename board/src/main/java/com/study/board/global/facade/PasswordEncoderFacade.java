package com.study.board.global.facade;

public interface PasswordEncoderFacade {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
