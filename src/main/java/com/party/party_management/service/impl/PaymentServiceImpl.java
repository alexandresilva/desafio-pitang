package com.party.party_management.service.impl;

import com.party.party_management.component.PaymentMapper;
import com.party.party_management.dto.PaymentRequestDTO;
import com.party.party_management.dto.PaymentResponseDTO;
import com.party.party_management.enumerate.PaymentStatus;
import com.party.party_management.exception.ResourceNotFoundException;
import com.party.party_management.model.Event;
import com.party.party_management.model.Payment;
import com.party.party_management.model.User;
import com.party.party_management.repository.EventRepository;
import com.party.party_management.repository.PaymentRepository;
import com.party.party_management.repository.UserRepository;
import com.party.party_management.service.PaymentProcessor;
import com.party.party_management.service.PaymentService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PaymentProcessor paymentProcessor;
    private final PaymentMapper paymentMapper;

    public PaymentServiceImpl(PaymentRepository paymentRepository, EventRepository eventRepository,
                              UserRepository userRepository, ModelMapper modelMapper, PaymentProcessor paymentProcessor,
                              PaymentMapper paymentMapper) {
        this.paymentRepository = paymentRepository;
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.paymentProcessor = paymentProcessor;
        this.paymentMapper = paymentMapper;
    }

    @Cacheable(value = "payments", key = "{#status, #eventId, #userId}")
    public List<PaymentResponseDTO> getAllPayments(String status, Long eventId, Long userId) {
        Specification<Payment> spec = Specification.where(null);

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        if (eventId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("event").get("id"), eventId));
        }

        if (userId != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("user").get("id"), userId));
        }

        return paymentRepository.findAll(spec).stream()
                .map(this::convertToDto)
                .toList();
    }

    private PaymentResponseDTO convertToDto(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getTransactionId(),
                payment.getAmount(),
                payment.getPaymentMethod(),
                payment.getPaymentDate(),
                payment.getStatus().name(),
                payment.getEvent().getId(),
                payment.getEvent().getTitle(),
                payment.getUser().getId(),
                payment.getUser().getEmail()
        );
    }

    @Transactional
    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        // 1. Validação adicional
        if (paymentRepository.existsByTransactionId(request.getTransactionId())) {
            throw new IllegalArgumentException("Transaction ID já existe");
        }

        // 2. Busca as entidades relacionadas
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new ResourceNotFoundException("Evento não encontrado"));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        // 3. Cria o pagamento
        Payment payment = new Payment();
        payment.setTransactionId(request.getTransactionId());
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setStatus(PaymentStatus.PENDING); // Status inicial
        payment.setEvent(event);
        payment.setUser(user);

        // 4. Salva no banco
        Payment savedPayment = paymentRepository.save(payment);

        // 5. Dispara processamento assíncrono
        paymentProcessor.processAsync(savedPayment);

        // 6. Retorna o DTO
        return convertToDto(savedPayment);
    }

    public PaymentResponseDTO findById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
        return paymentMapper.toResponse(payment);
    }

    public PaymentResponseDTO update(Long id, PaymentRequestDTO request) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        paymentMapper.updateFromRequest(request, payment);
        Payment updatedPayment = paymentRepository.save(payment);

        return paymentMapper.toResponse(updatedPayment);
    }

    public void cancel(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        payment.setStatus(PaymentStatus.CANCELLED);
        paymentRepository.save(payment);
    }
}
