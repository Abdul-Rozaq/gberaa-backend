package com.arktech.dto;

import com.arktech.util.TransactionStatus;
import com.arktech.util.TransactionType;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FundWalletRequest {

	private String paymentId;
	private Double amount;
	private TransactionStatus status;
	private TransactionType type;
	
	public FundWalletRequest(String paymentId, Double amount, TransactionStatus status, TransactionType type) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.status = TransactionStatus.success;
		this.type = TransactionType.credit;
	}
}
