package com.example.authservice.interfaces.rest;

import com.example.authservice.application.auth.PasswordLoginHandler;
import com.example.authservice.application.logout.LogoutHandler;
import com.example.authservice.application.token.RefreshTokenHandler;
import com.example.authservice.application.token.ValidateRefreshTokenHandler;
import com.example.authservice.interfaces.rest.dto.auth.PasswordLoginRequest;
import com.example.authservice.interfaces.rest.dto.auth.TokenResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final PasswordLoginHandler passwordLoginHandler;
    private final RefreshTokenHandler refreshTokenHandler;
    private final ValidateRefreshTokenHandler validateRefreshTokenHandler;
    private final LogoutHandler logoutHandler;

    @PostMapping("/login/password")
    public ResponseEntity<TokenResponse> loginWithPassword(@Valid @RequestBody PasswordLoginRequest request) {
        TokenResponse response = passwordLoginHandler.handle(request.email(), request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid @RequestBody String refreshTokenHash) {
        TokenResponse response = refreshTokenHandler.handle(refreshTokenHash);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validate(@Valid @RequestBody String refreshTokenHash) {
        boolean response = validateRefreshTokenHandler.handleTime(refreshTokenHash);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody String refreshTokenHash) {
        String response = logoutHandler.handle(refreshTokenHash);
        return ResponseEntity.ok(response);
    }

    
}
