package com.party.party_management.service.mock;

import com.party.party_management.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Profile("dev")
public class MockNotificationService implements NotificationService {

    private static final Logger log = LoggerFactory.getLogger(MockNotificationService.class);

    @Override
    public void sendPaymentConfirmation(String email, String eventTitle, BigDecimal amount) {
        log.info("[Mock Email] Enviando confirmação para: {}" +
                "Evento: {} " +
                "Valor: {}", email, eventTitle, amount);
    }
}