package com.party.party_management.service;

import com.party.party_management.exception.NotificationException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface NotificationService {
    void sendPaymentConfirmation(String email, String eventTitle, BigDecimal amount)
            throws NotificationException;
}