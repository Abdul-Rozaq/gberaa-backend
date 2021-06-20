package com.arktech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.dto.FundWalletRequest;
import com.arktech.service.TransactionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController {

	private TransactionService transactionService;
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAllTransactions() {
		return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
	}
	
	@GetMapping("/for-user")
	public ResponseEntity<ApiResponse> getAllTransactionsForUser() {
		return new ResponseEntity<>(transactionService.getAllTransactionsForUser(), HttpStatus.OK);
	}
	
	@PostMapping("/wallet")
	public ResponseEntity<ApiResponse> fundWallet(@RequestBody FundWalletRequest request) {
		return new ResponseEntity<>(transactionService.fundWallet(request), HttpStatus.CREATED);
	}
	
	@PostMapping("/payment")
	public ResponseEntity<ApiResponse> payForDelivery(@RequestParam("delivery") Long deliveryId) {
		
		return new ResponseEntity<>(transactionService.payForDelivery(deliveryId), HttpStatus.CREATED);
	}
}
