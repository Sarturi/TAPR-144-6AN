package com.example.authservice.application.token;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.domain.token.RefreshToken;
import com.example.authservice.domain.token.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateRefreshTokenHandler {
    private final RefreshTokenRepository repository;
    private final TokenService tokenService;

    private boolean handle(String refreshTokenHash) throws IllegalArgumentException {
        RefreshToken refreshToken = repository.findActiveByHash(refreshTokenHash)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));
        
        if (Instant.now().isAfter(refreshToken.getExpiresAt().getValue())) {
            throw new IllegalArgumentException("Expired refresh token");
        }

        if (!refreshToken.isActive()) {
            throw new IllegalArgumentException("Inactive refresh token");
        }

        return true;
    }
}
