package com.arktech.util;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum AppUserRole {
	USER("USER"),
	ADMIN("ADMIN"),
	RIDER("RIDER");
	
	private final String role;
	
	private AppUserRole(String role) {
		this.role = role;
	}
	
	public Set<? extends GrantedAuthority> getGrantedAuthorities() {
		Set<SimpleGrantedAuthority> role = new HashSet<>();
		role.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return role;
	}

	public String getRole() {
		return role;
	}
}
