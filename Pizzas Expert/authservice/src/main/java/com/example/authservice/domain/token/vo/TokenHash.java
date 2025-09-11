package com.example.authservice.domain.token.vo;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class TokenHash {
    @Column(name = "token_hash", length = 512, nullable = false)
    private String value;

    public TokenHash() {}

    public TokenHash(String value) {
        this.value = value;
    }

    public static TokenHash of(String value) {
        return new TokenHash(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenHash tokenHash = (TokenHash) o;
        return Objects.equals(value, tokenHash.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
