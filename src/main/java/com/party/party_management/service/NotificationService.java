package com.party.party_management.service;

import com.party.party_management.exception.NotificationException;
import jakarta.mail.MessagingException;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.math.BigDecimal;

public interface NotificationService {
    void sendPaymentConfirmation(String email, String eventTitle, BigDecimal amount)
            throws NotificationException;
}