package com.party.party_management.component;

import com.party.party_management.dto.PaymentRequestDTO;
import com.party.party_management.dto.PaymentResponseDTO;
import com.party.party_management.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    PaymentResponseDTO toResponse(Payment payment);
    @Mapping(target = "id", ignore = true)
    Payment toEntity(PaymentRequestDTO request);
    void updateFromRequest(PaymentRequestDTO request, @MappingTarget Payment payment);
}