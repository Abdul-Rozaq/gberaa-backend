package com.arktech.security;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.arktech.dto.LoginRequest;
import com.arktech.exception.AppException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	private AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			LoginRequest authRequest = new ObjectMapper().readValue(request.getInputStream(), LoginRequest.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authRequest.getUsername(), 
					authRequest.getPassword());
			
			Authentication authenticate = authenticationManager.authenticate(authentication);
			return authenticate;
			
		} catch (IOException e) {
			throw new AppException(e.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String key = "SecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKeySecuredKey";
		String token = Jwts.builder()
							.setSubject(authResult.getName())
							.claim("authorities", authResult.getAuthorities())
							.setIssuedAt(new Date())
							.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusWeeks(2)))
							.signWith(Keys.hmacShaKeyFor(key.getBytes()))
							.compact();
		
		SecurityContextHolder.getContext().setAuthentication(authResult);
				
		response.setHeader("Access-Control-Expose-Headers", "Authorization");	
		response.addHeader("Authorization", "Bearer " + token);
	
	}

	
}
