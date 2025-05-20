package com.party.party_management.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Test
    void testSendConfirmation() {
        emailNotificationService.sendPaymentConfirmation(
                "test@example.com",
                "Festa de Aniversário",
                new BigDecimal("199.99")
        );
    }
}