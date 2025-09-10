package com.example.authservice.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.authservice.application.port.TokenService;
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

        // esse é o token de acesso
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

        // criação do token, passando o token de acesso, de refresh, e o tempo de
        // expiração
        return new TokenPair(access, refresh, props.getAccessTtlSeconds());
    }
}
