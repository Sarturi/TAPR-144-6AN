package com.example.authservice.domain.token.vo;

import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class TokenHash {
    private String value;

    public TokenHash() {}

    public TokenHash(String value) {
        this.value = value;
    }

    public static TokenHash of(String value) {
        return new TokenHash(value);
    }
}
