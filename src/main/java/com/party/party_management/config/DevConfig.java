package com.party.party_management.config;

import jakarta.mail.internet.MimeMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.io.InputStream;
import java.util.Arrays;

@Configuration
@Profile("dev") // Só será carregada quando o profile 'dev' estiver ativo
public class DevConfig {

    @Bean
    @Primary
    public JavaMailSender mockMailSender() {
        return new JavaMailSender() {
            @Override
            public MimeMessage createMimeMessage() {
                return null;
            }

            @Override
            public MimeMessage createMimeMessage(InputStream contentStream) throws MailException {
                return null;
            }

            @Override
            public void send(MimeMessage... mimeMessages) throws MailException {

            }

            @Override
            public void send(SimpleMailMessage simpleMessage) {
                System.out.println("\n=== MOCK EMAIL ===");
                System.out.println("To: " + Arrays.toString(simpleMessage.getTo()));
                System.out.println("Subject: " + simpleMessage.getSubject());
                System.out.println("Content:\n" + simpleMessage.getText());
                System.out.println("===\n");
            }

            // Implemente outros métodos necessários com retornos vazios/default
            @Override
            public void send(SimpleMailMessage... simpleMessages) {
                for (SimpleMailMessage message : simpleMessages) {
                    send(message);
                }
            }
        };
    }
}
