package com.arktech.security;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.arktech.exception.AppException;
import com.arktech.util.Config;
import com.google.common.base.Strings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenVerifier extends OncePerRequestFilter {
	
	private Config config;

	@SuppressWarnings("unchecked")
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		if ( Strings.isNullOrEmpty(authHeader) || !authHeader.startsWith("Bearer ") ) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = authHeader.replace("Bearer ", "");
		
		try {
			Jws<Claims> claimsJws = Jwts.parserBuilder()
										.setSigningKey(Keys.hmacShaKeyFor(config.getKey().getBytes()))
										.build()
										.parseClaimsJws(token);
			
            Claims body = claimsJws.getBody(); // get body of the verified JWT token
            String username = body.getSubject(); // get the subject we used as the header
            List<Map<String, String>> authorities = (List<Map<String, String>>) body.get("authorities"); // claims of the JWT body (authorities)
			
            // convert the array of authorities in the claim of token to SGA
            Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.get("authority")))
                    .collect(Collectors.toSet());
            
            // verify the user token credentials and set its authorities to SimpleGrantedAuthorities
            Authentication authentication= new UsernamePasswordAuthenticationToken(
                    username, null, simpleGrantedAuthorities
            );

            // set authentication to true i.e we can trust the user
            SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (JwtException e) {
			throw new AppException("Please provide a valid token");
		}
		
		filterChain.doFilter(request, response);
	}

}
