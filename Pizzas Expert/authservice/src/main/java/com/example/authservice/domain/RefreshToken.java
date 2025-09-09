package com.example.authservice.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RefreshToken {
    private String tokenHash;
    private String expiresAt;
}
