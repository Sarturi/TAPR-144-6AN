package com.example.authservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authservice.application.port.TokenService;
import com.example.authservice.application.token.EmitRefreshTokenHandler;
import com.example.authservice.domain.token.RefreshToken;
import com.example.authservice.domain.token.RefreshTokenRepository;
import com.example.authservice.domain.user.User;
import com.example.authservice.infrastructure.config.JwtProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenService implements TokenService {
    private final JwtProperties props;
    private final EmitRefreshTokenHandler emitRefreshTokenHandler;
    private final RefreshTokenRepository refreshTokenRepository;
    // private final ValidateRefreshTokenHandler validateRefresh;

    @Override
    public TokenPair issue(User user) {
        if (props.getSecret() == null || props.getSecret().isBlank()) {
            throw new IllegalStateException("jwt.secret deve ser definido");
        }

        // criação do algoritimo de hash
        Instant now = Instant.now();
        Algorithm alg = Algorithm.HMAC256(props.getSecret().getBytes(StandardCharsets.UTF_8));

        // tempo de expiração do acesso, somando o tempo de agora com o tempo de acesso
        // do jwt properties
        Instant accessExp = now.plusSeconds(props.getAccessTtlSeconds());

        // esse é o token de acesso curto
        String access = JWT.create()
                .withIssuer(props.getIssuer())
                .withAudience(props.getAudience())
                .withSubject(user.getId().toString())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(accessExp))
                .withClaim("type", "access")
                .withClaim("email", user.getEmail().getValue())
                .withClaim("role", user.getRole().getValue().name())
                .withClaim("level", user.getRole().getValue().getLevel())
                .sign(alg);

        Instant refreshExp = now.plusSeconds(props.getRefresTtlSeconds());

        // token de refresh
        String refresh = JWT.create()
                .withIssuer(props.getIssuer())
                .withAudience(props.getAudience())
                .withSubject(user.getId().toString())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(refreshExp))
                .withClaim("type", "refresh")
                .withClaim("email", user.getEmail().getValue())
                .withClaim("role", user.getRole().getValue().name())
                .withClaim("level", user.getRole().getValue().getLevel())
                .sign(alg);

                
        emitRefreshTokenHandler.handle(refresh, refreshExp, user);

        // criação do token, passando o token de acesso, de refresh, e o tempo de
        // expiração
        return new TokenPair(access, refresh, props.getAccessTtlSeconds());
    }

    @Override
    public TokenPair refresh(String refreshTokenHash) {
        // System.out.println("Recebido refreshTokenHash: " + refreshTokenHash);
        // Optional<RefreshToken> optToken = refreshTokenRepository.findActiveByHash(refreshTokenHash);
        // System.out.println("RefreshToken encontrado: " + optToken.get());
        // System.out.println("Hash do refreshtoken encontrado: " + optToken.get().getTokenHash().getValue());
        // System.out.println("Encontrou token no DB? " + optToken.isPresent());
        // RefreshToken refreshToken = optToken.orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        RefreshToken refreshToken = refreshTokenRepository.findActiveByHash(refreshTokenHash)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        // talvez validar se o refreshToken está ativo ou não (validateRefreshTokenHandler)
        // if (!validateRefresh.handle(refreshTokenHash)) {
        //     System.out.println("Could not refresh");
        // }

        refreshTokenRepository.revoke(refreshToken);
        refreshTokenRepository.save(refreshToken);

        User user = refreshToken.getUser();
        return issue(user);
    }

    @Override
    public void revoke(String refreshTokenHash) {
        RefreshToken refreshToken = refreshTokenRepository.findActiveByHash(refreshTokenHash)
            .orElseThrow(() -> new IllegalArgumentException("Invalid refresh token"));

        // talvez validar se o refreshToken está ativo ou não (validateRefreshTokenHandler)

        refreshTokenRepository.revoke(refreshToken);
        refreshTokenRepository.save(refreshToken);
    }
}
