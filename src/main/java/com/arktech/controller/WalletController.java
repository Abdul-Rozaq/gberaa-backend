package com.arktech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.service.WalletService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/wallets")
public class WalletController {

	private WalletService walletService;
	
	@GetMapping
	public ResponseEntity<ApiResponse> getWalletByUser() {
		return new ResponseEntity<>(walletService.getWalletByUser(), HttpStatus.OK);
	}
}
