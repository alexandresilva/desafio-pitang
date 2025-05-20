package com.party.party_management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(
        Long paymentId,
        String transactionId,
        BigDecimal amount,
        String paymentMethod,
        LocalDateTime paymentDate,
        String status,
        Long eventId,
        String eventTitle,
        Long userId,
        String userEmail
) {}