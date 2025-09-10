package com.example.authservice.domain.token.vo;

import java.time.Instant;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ExpiresAt {
    private Instant value;

    public ExpiresAt() {}

    public ExpiresAt(Instant value) {
        this.value = value;
    }

    public static ExpiresAt of(Instant value) {
        return new ExpiresAt(value);
    }
}
