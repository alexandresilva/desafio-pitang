package com.party.party_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PaymentRequest(
        @NotBlank(message = "Transaction ID é obrigatório")
        String transactionId,

        @NotNull(message = "Valor é obrigatório")
        @Positive(message = "Valor deve ser positivo")
        BigDecimal amount,

        @NotBlank(message = "Método de pagamento é obrigatório")
        String paymentMethod,

        @NotNull(message = "ID do evento é obrigatório")
        Long eventId,

        @NotNull(message = "ID do usuário é obrigatório")
        Long userId
) {}