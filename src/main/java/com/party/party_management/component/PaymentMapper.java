package com.party.party_management.component;

import com.party.party_management.dto.PaymentRequest;
import com.party.party_management.dto.PaymentResponse;
import com.party.party_management.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponse toResponse(Payment payment);
    Payment toEntity(PaymentRequest request);
    void updateFromRequest(PaymentRequest request, @MappingTarget Payment payment);
}