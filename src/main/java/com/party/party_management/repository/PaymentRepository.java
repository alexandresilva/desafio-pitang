package com.party.party_management.repository;

import com.party.party_management.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>, JpaSpecificationExecutor<Payment> {

    // Verifica se já existe um pagamento com o transactionId
    boolean existsByTransactionId(String transactionId);

    // Outros métodos podem ser adicionados aqui
    boolean existsByEventIdAndUserId(Long eventId, Long userId);
}