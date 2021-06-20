package com.arktech.dto;

import java.time.LocalDateTime;

import com.arktech.util.TransactionStatus;
import com.arktech.util.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TransactionResponse {

	private Long id;
	private String paymentId;
	private Double amount;
	private TransactionStatus status;
	private LocalDateTime paymentDate;
	private TransactionType type;
	
	private String userEmail;
	private String userPhoneNumber;
	private String userLastname;
	
	private String riderEmail;
	private String riderPhoneNumber;
	private String riderLastname;
	
	private Long deliveryId;
	private String deliveryType;
}
