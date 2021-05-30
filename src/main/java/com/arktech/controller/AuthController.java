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

import com.arktech.dto.RegistrationRequest;
import com.arktech.service.AuthService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private AuthService authService; 
	
	@PostMapping("/signup")
	public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
		String authResponse = authService.register(request);
		return new ResponseEntity<>(authResponse, HttpStatus.OK);
	}
	
	@GetMapping("/account/verification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable("token") String token) {
		String response = authService.verifyAccount(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}