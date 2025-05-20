package com.party.party_management.service;

import com.party.party_management.dto.PaymentRequestDTO;
import com.party.party_management.dto.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {
    List<PaymentResponseDTO> getAllPayments(String status, Long eventId, Long userId);
    PaymentResponseDTO createPayment(PaymentRequestDTO request);
    PaymentResponseDTO findById(Long id); // Nome do método igual ao primeiro exemplo
    PaymentResponseDTO update(Long id, PaymentRequestDTO request); // Nome consistente
    void cancel(Long id); // Igual ao original
}
