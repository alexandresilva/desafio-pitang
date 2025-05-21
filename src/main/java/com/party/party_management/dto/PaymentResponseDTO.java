package com.party.party_management.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
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

	public PaymentResponseDTO(Long paymentId, String transactionId, BigDecimal amount,
							  String paymentMethod, LocalDateTime paymentDate,
							  String status, Long eventId, String eventTitle,
							  Long userId, String userEmail) {
		this.paymentId = paymentId;
		this.transactionId = transactionId;
		this.amount = amount;
		this.paymentMethod = paymentMethod;
		this.paymentDate = paymentDate;
		this.status = status;
		this.eventId = eventId;
		this.eventTitle = eventTitle;
		this.userId = userId;
		this.userEmail = userEmail;
	}

	public PaymentResponseDTO() {
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

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

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public String getEventTitle() {
		return eventTitle;
	}

	public void setEventTitle(String eventTitle) {
		this.eventTitle = eventTitle;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
}