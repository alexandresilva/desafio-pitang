package com.party.party_management.service;

import com.party.party_management.dto.PaymentRequest;
import com.party.party_management.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    List<PaymentResponse> getAllPayments(String status, Long eventId, Long userId);
    PaymentResponse createPayment(PaymentRequest request);
    PaymentResponse findById(Long id); // Nome do método igual ao primeiro exemplo
    PaymentResponse update(Long id, PaymentRequest request); // Nome consistente
    void cancel(Long id); // Igual ao original
}
