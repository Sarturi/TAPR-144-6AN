package com.example.authservice.domain.token.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class ExpiresAt {
    private String value;

    public ExpiresAt() {}

    public ExpiresAt(String value) {
        this.value = value;
    }

    public static ExpiresAt of(String value) {
        return new ExpiresAt(value);
    }
}
