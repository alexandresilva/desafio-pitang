package com.party.party_management.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
	private Long paymentId;
	private String transactionId;
	private BigDecimal amount;
	private String paymentMethod;
	private LocalDateTime paymentDate;
	private String status;
	private Long eventId;
	private String eventTitle;
	private Long userId;
	private String userEmail;
}