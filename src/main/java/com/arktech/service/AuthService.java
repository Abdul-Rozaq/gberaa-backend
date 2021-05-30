package com.arktech.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.arktech.Repository.VerificationTokenRepository;
import com.arktech.dto.RegistrationRequest;
import com.arktech.entity.NotificationEmail;
import com.arktech.entity.User;
import com.arktech.entity.VerificationToken;
import com.arktech.exception.AppException;
import com.arktech.util.AppUserRole;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
	private static final String SIGN_UP_MESSAGE = "Thank you for signing up to Gberaa. Please click the verification Link ";
	private static final String ACTIVATION_EMAIL_LINK = "http://localhost:8080/api/auth/accountVerification/";
	
	private PasswordEncoder passwordEncoder;
	private MailContentBuilder mailBuilder;
	private MailService mailService;
	private UserService userService;
	private VerificationTokenRepository tokenRepository;

	public String register(RegistrationRequest request) {
		User user = new User();
		user.setFirstName(request.getFirstName());
		user.setLastName(request.getLastName());
		user.setEmail(request.getEmail());
		user.setPhone(request.getPhone());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setEnabled(false);
		user.setAddress(request.getAddress());
		user.setCreatedDate(Instant.now());
		
		if (request.getRole() == null) {
			user.setRole(AppUserRole.USER);
		} else {
			user.setRole(request.getRole());			
		}
		
		User _user = userService.save(user);
		String token = generateVerificationToken(_user);
		sendVerificationEmail(_user, token);
		
		return "Registration complete! Please check your mail for verification.";
	}

	public String verifyAccount(String token) {
		VerificationToken _token = tokenRepository.findByToken(token).orElseThrow(() -> new AppException("Token not found"));
		
		if (_token.getConfirmedAt() != null) 
			throw new AppException("Email already verified");
		
		LocalDateTime expiryDate = _token.getExpiryDate();
		if (expiryDate.isBefore(LocalDateTime.now())) 
			throw new AppException("Token already expired, please request a new one");
		
		fetchAndEnableUser(_token);
		return "Account verification Successful";
	}
	
	public User getCurrentUser() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		return userService.findUserByEmail(username);
	}
	
	// HELPER METHODS
	
	private void fetchAndEnableUser(VerificationToken token) {
		String email = token.getUser().getEmail();
		userService.findUserByEmail(email);
		
		token.setConfirmedAt(LocalDateTime.now());
		userService.enableAppUser(email);
		tokenRepository.save(token);
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken _token = new VerificationToken(null, token, user, LocalDateTime.now().plusDays(14), null);
		tokenRepository.save(_token);
		
		return token;
	}
	
	private void sendVerificationEmail(User user, String token) {
		String message = mailBuilder.build(SIGN_UP_MESSAGE + ACTIVATION_EMAIL_LINK + token);
		mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(), message));
	}
}
