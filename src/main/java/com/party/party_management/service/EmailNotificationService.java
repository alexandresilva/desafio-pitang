package com.party.party_management.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class EmailNotificationService implements NotificationService {
    private final JavaMailSender mailSender;
    private final Environment env;

    public EmailNotificationService(@Value("${spring.profiles.active}") String activeProfile,
                                    JavaMailSender javaMailSender, JavaMailSender mockMailSender, Environment env){
        this.mailSender = activeProfile.contains("dev") ? mockMailSender : javaMailSender;
        this.env = env;
    }

    @Override
    public void sendPaymentConfirmation(String email, String eventTitle, BigDecimal amount) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(env.getProperty("spring.mail.username", "no-reply@partymanagement.com"));
        message.setTo(email);
        message.setSubject("Confirmação de Pagamento");
        message.setText(String.format(
                "Pagamento para o evento %s no valor de %s confirmado!",
                eventTitle,
                amount
        ));
        mailSender.send(message);
    }
}