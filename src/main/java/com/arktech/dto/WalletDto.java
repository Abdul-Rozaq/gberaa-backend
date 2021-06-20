package com.arktech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class WalletDto {

	private String email;
	private Double balance;
}
