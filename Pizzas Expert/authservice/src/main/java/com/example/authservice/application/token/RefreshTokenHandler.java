package com.example.authservice.application.token;

import org.springframework.stereotype.Service;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.domain.token.RefreshTokenRepository;
import com.example.authservice.interfaces.rest.dto.auth.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenHandler {
    private final RefreshTokenRepository repository;
    private final TokenService tokenService;

    public TokenResponse handle(String refreshTokenHash){
        TokenService.TokenPair pair = tokenService.refresh(refreshTokenHash);

        return new TokenResponse(
                pair.accessToken(),
                pair.refreshToken(),
                pair.expiresInSeconds()
        );
    }
}
