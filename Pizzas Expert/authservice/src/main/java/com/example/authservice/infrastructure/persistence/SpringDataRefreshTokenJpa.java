package com.example.authservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.authservice.domain.token.RefreshToken;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshToken, UUID> {
    Optional<RefreshToken> findActiveByHash(String hash);
    void revoke(RefreshToken refreshToken);
}
