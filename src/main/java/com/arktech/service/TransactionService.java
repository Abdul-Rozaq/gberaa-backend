package com.arktech.service;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.arktech.Repository.DeliveryRepository;
import com.arktech.Repository.TransactionRepository;
import com.arktech.Repository.WalletRepository;
import com.arktech.dto.ApiResponse;
import com.arktech.dto.FundWalletRequest;
import com.arktech.entity.Delivery;
import com.arktech.entity.Transaction;
import com.arktech.entity.User;
import com.arktech.entity.Wallet;
import com.arktech.exception.AppException;
import com.arktech.mapper.TransactionMapper;
import com.arktech.util.TransactionStatus;
import com.arktech.util.TransactionType;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class TransactionService {
	
	private TransactionRepository transactionRepository;
	private TransactionMapper transactionMapper;
	private WalletRepository walletRepository;
	private DeliveryRepository deliveryRepository;
	private AuthService authService;
	
	public ApiResponse getAllTransactions() {
		try {
			var transaction = transactionRepository
												.findAll()
												.stream()
												.map(transactionMapper::mapToResponse)
												.collect(Collectors.toList());
			
			return new ApiResponse("Successful", null, transaction);
			
		} catch (Exception e) {
			return new ApiResponse("Error", e.getMessage(), null);
		}
	}
	
	public ApiResponse getAllTransactionsForUser() {
		try {
			User user = authService.getCurrentUser();
			
			var transactions = transactionRepository
												.findByUser(user)
												.stream()
												.map(transactionMapper::mapToResponse)
												.collect(Collectors.toList());
			
			return new ApiResponse("Successful", null, transactions);
			
		} catch (Exception e) {
			return new ApiResponse("Error", e.getMessage(), null);
		}
	}

	public ApiResponse fundWallet(FundWalletRequest request) {
		try {
			User user = authService.getCurrentUser();
			
			Transaction transaction = transactionMapper.mapToWallet(request, user);
			transactionRepository.save(transaction);
			
			Wallet wallet = walletRepository
									.findByUser(user)
									.orElseThrow(() -> new AppException("user does not have a wallet account"));
			
			wallet.setUser(user);
			wallet.setBalance(wallet.getBalance() + request.getAmount());
			
			walletRepository.save(wallet);
			
			return new ApiResponse("Successful", "Transaction successful", null);
			
		} catch (Exception e) {
			return new ApiResponse("Error", e.getMessage(), null);
		}
	}

	public ApiResponse payForDelivery(Long deliveryId) {
		try {
			User user = authService.getCurrentUser();
			
			Delivery delivery = deliveryRepository
					.findById(deliveryId)
					.orElseThrow(() -> new AppException("Delivery not found"));
			
			Wallet wallet = walletRepository.findByUser(user).orElseThrow(() -> new AppException("user does not have a wallet"));
			
			Transaction transaction = new Transaction();
			
			if (wallet.getBalance() < delivery.getPrice()) {
				transaction = transactionMapper.mapToPayment(delivery, user, TransactionStatus.declined);
				transaction.setStatus(TransactionStatus.declined);
				transaction.setType(TransactionType.debit);
				transactionRepository.save(transaction);
				
				throw new AppException("Insufficient funds, Please fund your wallet");
			}
			
			transaction = transactionMapper.mapToPayment(delivery, user, TransactionStatus.success);
			transaction.setType(TransactionType.debit);
			transactionRepository.save(transaction);
			
			wallet.setBalance(wallet.getBalance() - delivery.getPrice());
			walletRepository.save(wallet);
			
			return new ApiResponse("Successful", "Payment successful for delivery " + deliveryId, null);
		} catch (Exception e) {
			return new ApiResponse("Error", e.getMessage(), null);
		}
	}

}
