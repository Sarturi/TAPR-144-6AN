package com.example.authservice.infrastructure.mail;

import com.example.authservice.application.port.MailSender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
@Profile({"prod"})
public class SmtpMailSender implements MailSender {
    private static final Logger log = LoggerFactory.getLogger(LogMailSender.class);

    // método que envia o link mágico para o log,
    // deve ser usado somente para prod
    @Override
    public void sendMagicLink(String toEmail, String magicUrl, Instant expiresAt) {
        log.info("[PROD] Magic Link para {}: {} (expira em {})", toEmail, magicUrl, expiresAt);
    }
}
