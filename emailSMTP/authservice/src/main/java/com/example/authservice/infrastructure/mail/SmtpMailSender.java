package com.example.authservice.infrastructure.mail;

import com.example.authservice.application.port.MailSender;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
@Profile({"prod"})
public class SmtpMailSender implements MailSender {
    private final JavaMailSender mailSender;

    // método que envia o link mágico para o email,
    // deve ser usado somente para prod
    @Override
    public void sendMagicLink(String toEmail, String magicUrl, Instant expiresAt) {
        String data = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.of("America/Sao_Paulo"))
            .format(expiresAt);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("tapr-1446an@dominio.com");
        message.setTo(toEmail);
        message.setSubject("Faça login em tapr-emailSMTP:");
        message.setText("Link: " + magicUrl + "   " + "Expira em: " + data);

        mailSender.send(message);
    }
}
