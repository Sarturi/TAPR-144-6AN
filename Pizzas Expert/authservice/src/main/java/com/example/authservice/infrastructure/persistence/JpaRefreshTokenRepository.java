package com.example.authservice.infrastructure.persistence;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.example.authservice.domain.token.RefreshToken;
import com.example.authservice.domain.token.RefreshTokenRepository;

@Repository
public class JpaRefreshTokenRepository implements RefreshTokenRepository{
    private final SpringDataRefreshTokenJpa jpa;

    public JpaRefreshTokenRepository(SpringDataRefreshTokenJpa jpa) {
        this.jpa = jpa;
    }

    @Override
    public void deleteById(UUID id) {
        jpa.deleteById(id);
    }
    @Override
    public Optional<RefreshToken> findActiveByHash(String hash) {
        return jpa.findActiveByHash(hash);
    }
    @Override
    public void revoke(RefreshToken refreshToken) {
       jpa.revoke(refreshToken); 
    }
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return jpa.save(refreshToken);
    }
    
}
