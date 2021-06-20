package com.arktech.service;

import org.springframework.stereotype.Service;

import com.arktech.Repository.WalletRepository;
import com.arktech.dto.ApiResponse;
import com.arktech.dto.WalletDto;
import com.arktech.entity.User;
import com.arktech.entity.Wallet;
import com.arktech.exception.AppException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WalletService {

	private WalletRepository walletRepository;
	private AuthService authService;
	
	public ApiResponse getWalletByUser() {
		try {
			User user = authService.getCurrentUser();
			
			Wallet wallet= walletRepository
					.findByUser(user)
					.orElseThrow(() -> new AppException("User does not have a wallet"));
			
			var walletDto = new WalletDto(wallet.getUser().getEmail(), wallet.getBalance());
			
			return new ApiResponse("Successful", null, walletDto);
			
		} catch (Exception e) {
			return new ApiResponse("Error", e.getMessage(), null);
		}
	}
}
