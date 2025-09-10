package com.example.authservice.application.logout;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.application.token.ValidateRefreshTokenHandler;
import com.example.authservice.domain.token.RefreshToken;
import com.example.authservice.domain.token.RefreshTokenRepository;
import com.example.authservice.domain.token.vo.ExpiresAt;
import com.example.authservice.domain.token.vo.TokenHash;
import com.example.authservice.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutHandler {
    private final RefreshTokenRepository repository;
    private final TokenService tokenService;
    private final ValidateRefreshTokenHandler validateRefresh;

    public String handle(String refreshTokenHash){ //User user
        if (!validateRefresh.handle(refreshTokenHash)) {
            return "Could not logout";
        }

        tokenService.revoke(refreshTokenHash);

        return "Sucessfully logout";
    }
}
