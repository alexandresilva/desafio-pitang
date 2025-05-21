package com.party.party_management.service;

import com.party.party_management.enumerate.PaymentStatus;
import com.party.party_management.model.Payment;
import com.party.party_management.repository.PaymentRepository;
import com.party.party_management.service.mock.MockNotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessor {

    private static final Logger log = LoggerFactory.getLogger(MockNotificationService.class);
    private final PaymentRepository paymentRepository;
    private final EmailNotificationService emailNotificationService;

    public PaymentProcessor(PaymentRepository paymentRepository, EmailNotificationService emailNotificationService){
        this.paymentRepository = paymentRepository;
        this.emailNotificationService = emailNotificationService;
    }

    @Async
    public void processAsync(Payment payment) {
        try {
            // Simula processamento
            Thread.sleep(3000);

            payment.setStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);

            // Envia notificação
            emailNotificationService.sendPaymentConfirmation(
                    payment.getUser().getEmail(),
                    payment.getEvent().getTitle(),
                    payment.getAmount()
            );

        } catch (Exception e) {
            payment.setStatus(PaymentStatus.FAILED);
            paymentRepository.save(payment);
            log.error("Falha ao processar pagamento", e);
        }
    }
}