package com.example.authservice.application.token;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.example.authservice.domain.token.RefreshToken;
import com.example.authservice.domain.token.RefreshTokenRepository;
import com.example.authservice.domain.token.vo.ExpiresAt;
import com.example.authservice.domain.token.vo.TokenHash;
import com.example.authservice.domain.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmitRefreshTokenHandler {
    private final RefreshTokenRepository repository;

    public RefreshToken handle(String hashRaw, Instant expiresAtRaw, User user){
        TokenHash hash = TokenHash.of(hashRaw);
        ExpiresAt expiresAt = ExpiresAt.of(expiresAtRaw);

        RefreshToken refToken = new RefreshToken(hash, expiresAt, user);
        RefreshToken savedRefToken = repository.save(refToken);
        // System.out.println("EmitRefresh hashRaw: " + hashRaw);
        // System.out.println("EmitRefresh savedToken hashRaw: " + savedRefToken.getTokenHash().getValue());

        return savedRefToken;
    }
}
