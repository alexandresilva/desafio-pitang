package com.party.party_management.service;

import com.party.party_management.enumerate.PaymentStatus;
import com.party.party_management.model.Payment;
import com.party.party_management.repository.EventRepository;
import com.party.party_management.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentProcessor {
    private final PaymentRepository paymentRepository;
    private final EventRepository eventRepository;
    private final NotificationService notificationService;

    @Async
    public void processAsync(Payment payment) {
        try {
            // Simula processamento
            Thread.sleep(3000);

            payment.setStatus(PaymentStatus.PAID);
            paymentRepository.save(payment);

            // Envia notificação
            notificationService.sendPaymentConfirmation(
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