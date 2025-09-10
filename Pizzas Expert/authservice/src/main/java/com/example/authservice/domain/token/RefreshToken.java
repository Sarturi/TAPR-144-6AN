package com.example.authservice.domain.token;

import java.util.UUID;

import com.example.authservice.domain.token.vo.ExpiresAt;
import com.example.authservice.domain.token.vo.TokenHash;
import com.example.authservice.domain.user.User;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public RefreshToken(TokenHash tokenHash, ExpiresAt expiresAt, User user) {
        this.tokenHash = tokenHash;
        this.expiresAt = expiresAt;
        this.active = true;
        this.user = user;
    }

    public void revoke() {
        this.active = false;
    }
}
