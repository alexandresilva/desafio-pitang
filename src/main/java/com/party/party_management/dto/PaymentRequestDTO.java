package com.party.party_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
	@NotBlank(message = "Transaction ID é obrigatório")
	private String transactionId;

	@NotNull(message = "Valor é obrigatório")
	@Positive(message = "Valor deve ser positivo")
	private BigDecimal amount;

	@NotBlank(message = "Método de pagamento é obrigatório")
	private String paymentMethod;

	@NotNull(message = "ID do evento é obrigatório")
	private Long eventId;

	@NotNull(message = "ID do usuário é obrigatório")
	private Long userId;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}