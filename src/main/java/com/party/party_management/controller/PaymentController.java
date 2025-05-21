package com.party.party_management.controller;

import com.party.party_management.dto.PaymentRequestDTO;
import com.party.party_management.dto.PaymentResponseDTO;
import com.party.party_management.security.UserDetailsImpl;
import com.party.party_management.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long eventId,
            @RequestParam(required = false) Long userId) {

        List<PaymentResponseDTO> payments = paymentService.getAllPayments(status, eventId, userId);
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @Valid @RequestBody PaymentRequestDTO request,
            Authentication authentication) {

        // Verifica se o usuário autenticado é o mesmo do pagamento
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        if (!userDetails.getId().equals(request.getUserId())) {
            throw new AccessDeniedException("Você só pode criar pagamentos para sua própria conta");
        }

        PaymentResponseDTO response = paymentService.createPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Long id) {
        PaymentResponseDTO payment = paymentService.findById(id);
        return ResponseEntity.ok(payment); // Retorna 200 OK com o pagamento
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(
            @PathVariable Long id,
            @RequestBody PaymentRequestDTO request
    ) {
        PaymentResponseDTO updatedPayment = paymentService.update(id, request);
        return ResponseEntity.ok(updatedPayment); // Retorna 200 OK com o pagamento atualizado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long id) {
        paymentService.cancel(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content (sucesso sem corpo)
    }
}