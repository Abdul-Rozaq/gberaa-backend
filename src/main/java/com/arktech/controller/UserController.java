package com.arktech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.dto.UserDto;
import com.arktech.entity.User;
import com.arktech.mapper.UserMapper;
import com.arktech.service.AuthService;
import com.arktech.service.UserService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

	private UserService userService;
	private AuthService authService;
	private UserMapper userMapper;
	
	@GetMapping("/me")
	public ResponseEntity<UserDto> getCurrentUser() {
		User user = authService.getCurrentUser();
		return new ResponseEntity<>(userMapper.mapToDto(user), HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAllUsers() {
		ApiResponse users = userService.getAllUsers();
		return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	@GetMapping("/riders")
	public ResponseEntity<ApiResponse> getAllRiders() {
		ApiResponse riders = userService.getAllRiders();
		return new ResponseEntity<>(riders, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<Void> updateUser(@RequestBody User user) {
		userService.updateUser(user);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
