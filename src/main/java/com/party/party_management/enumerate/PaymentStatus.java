package com.party.party_management.enumerate;

public enum PaymentStatus {
    PENDING,    // Aguardando pagamento
    PAID,       // Pagamento confirmado
    CANCELLED,  // Pagamento cancelado
    REFUNDED,   // Reembolsado (opcional)
    FAILED      // Pagamento Falhou
}
