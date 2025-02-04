package com.study.board.global.config;

import com.study.board.global.facade.BCryptPasswordEncoderFacade;
import com.study.board.global.facade.PasswordEncoderFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // 허용할 엔드포인트들을 패턴정의
    private static final String[] AUTH_WHITELIST = {
            // Swagger
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**",
            // 회원가입 API
            "/api/v1/users/register",
            "/api/v1/books/category",
            "/api/v1/reviews/**"
    };

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordEncoderFacade passwordEncoderFacade(PasswordEncoder passwordEncoder) {
        return new BCryptPasswordEncoderFacade(passwordEncoder);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //CSRF 비활성화
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
