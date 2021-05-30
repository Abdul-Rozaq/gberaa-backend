package com.arktech.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.arktech.Repository.UserRepository;
import com.arktech.dto.UserDto;
import com.arktech.entity.User;
import com.arktech.exception.AppException;
import com.arktech.mapper.UserMapper;
import com.arktech.util.AppUserRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class UserService {
	
	private UserMapper userMapper;
	private UserRepository userRepository;
	
	public User findUserByEmail(String email) {
		return userRepository
				.findByEmail(email)
				.orElseThrow(() -> new AppException("Account not found with Email: " + email));
	}
	
	public User findById(Long id) {
		return userRepository
				.findById(id)
				.orElseThrow(() -> new AppException("Account not found with Id: " + id));
	}
	
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public int enableAppUser(String email) {
		return userRepository.enableAppUser(email);
	}

	public List<UserDto> getAllUsers() {
		return userRepository.findByRole(AppUserRole.USER)
				.stream()
				.map(userMapper::mapToDto)
				.collect(Collectors.toList());
	}
	
	public List<UserDto> getAllRiders() {
		return userRepository.findByRole(AppUserRole.RIDER)
				.stream()
				.map(userMapper::mapRiderToDto)
				.collect(Collectors.toList());
	}

	public void updateUser(User user) {
		User _user = userRepository
						.findById(user.getId())
						.orElseThrow(() -> new AppException("Account not found with Id: " + user.getId()));
		
		_user.setId(_user.getId());
		_user.setFirstName(user.getFirstName());
		_user.setLastName(user.getLastName());
		_user.setEmail(user.getEmail());
		_user.setPhone(user.getPhone());
		_user.setAddress(user.getAddress());
		_user.setRole(_user.getRole());
		_user.setPassword(_user.getPassword());
		_user.setCreatedDate(_user.getCreatedDate());
		_user.setEnabled(_user.getEnabled());
		
		userRepository.save(_user);
	}
}
