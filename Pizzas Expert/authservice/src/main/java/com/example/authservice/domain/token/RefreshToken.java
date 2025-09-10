package com.example.authservice.domain.token;

import java.util.UUID;

import com.example.authservice.domain.token.vo.ExpiresAt;
import com.example.authservice.domain.token.vo.TokenHash;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshToken {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Embedded
    private TokenHash tokenHash;

    @Embedded
    private ExpiresAt expiresAt;

    private boolean active;

    public RefreshToken(TokenHash tokenHash, ExpiresAt expiresAt, boolean active) {
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.active = active;
    }
}
