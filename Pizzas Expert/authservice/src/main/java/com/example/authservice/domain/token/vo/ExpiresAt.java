package com.example.authservice.domain.token.vo;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ExpiresAt {
    @Column(name = "expires_at", nullable = false)
    private Instant value;

    public ExpiresAt() {}

    public ExpiresAt(Instant value) {
        this.value = value;
    }

    public static ExpiresAt of(Instant value) {
        return new ExpiresAt(value);
    }
}
