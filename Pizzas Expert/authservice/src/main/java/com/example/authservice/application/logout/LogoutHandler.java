package com.example.authservice.application.logout;

import org.springframework.stereotype.Service;

import com.example.authservice.application.port.TokenService;
import com.example.authservice.application.token.ValidateRefreshTokenHandler;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutHandler {
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
