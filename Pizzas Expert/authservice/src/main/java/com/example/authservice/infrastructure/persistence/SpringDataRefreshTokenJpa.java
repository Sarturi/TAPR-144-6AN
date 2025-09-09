package com.example.authservice.infrastructure.persistence;

import com.example.authservice.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataRefreshTokenJpa extends JpaRepository<RefreshToken, UUID> {

}
