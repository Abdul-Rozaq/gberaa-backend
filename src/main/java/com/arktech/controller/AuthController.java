package com.arktech.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.dto.RegistrationRequest;
import com.arktech.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService; 
	
	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegistrationRequest request) {
		ApiResponse authResponse = authService.register(request);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@GetMapping("/account/verification/{token}")
	public ResponseEntity<ApiResponse> verifyAccount(@PathVariable("token") String token) {
		ApiResponse response = authService.verifyAccount(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
