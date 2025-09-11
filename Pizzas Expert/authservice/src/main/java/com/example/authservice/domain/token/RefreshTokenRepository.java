package com.example.authservice.domain.token;

import java.util.Optional;
import java.util.UUID;

public interface RefreshTokenRepository {
    RefreshToken save(RefreshToken refreshToken);
    Optional<RefreshToken> findActiveByHash(String hash);
    void revoke(RefreshToken refreshToken);
    void deleteById(UUID id);
}