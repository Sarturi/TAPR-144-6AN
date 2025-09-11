package com.example.authservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.authservice.domain.token.RefreshToken;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshToken, UUID> {
    @Query("SELECT r FROM RefreshToken r WHERE r.tokenHash.value = :hash")
    Optional<RefreshToken> findActiveByHash(@Param("hash") String hash);

    @Transactional
    @Modifying
    @Query("UPDATE RefreshToken r SET r.active = false WHERE r = :refreshToken")
    void revoke(@Param("refreshToken") RefreshToken refreshToken);
}
