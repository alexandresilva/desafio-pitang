package com.party.party_management.service;

import com.party.party_management.dto.PaymentRequestDTO;
import com.party.party_management.dto.PaymentResponseDTO;

import java.util.List;

public interface PaymentService {
    List<PaymentResponseDTO> getAllPayments(String status, Long eventId, Long userId);
    PaymentResponseDTO createPayment(PaymentRequestDTO request);
    PaymentResponseDTO findById(Long id);
    PaymentResponseDTO update(Long id, PaymentRequestDTO request);
    void cancel(Long id);
}
