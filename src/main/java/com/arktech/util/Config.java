package com.arktech.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {
	
	@Value("${jwt.expiration.time}")
	private Long jwtExpirationInMillis;
	
	@Value("${jwt.key}")
	private String jwtKey;

	@Bean
	public String getKey() {
		return jwtKey;
	}
	
	@Bean
	public Long getJwtExpirationInMillis() {
		return jwtExpirationInMillis;
	}
}
