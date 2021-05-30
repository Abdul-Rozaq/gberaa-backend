package com.arktech.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.arktech.entity.AppUser;
import com.arktech.entity.User;

import lombok.AllArgsConstructor;

/**
 * @author HP
 *
 * helps spring security to validate our users by passing in our own database reference
 */

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {
	
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByEmail(username);
		
		return new AppUser(
				user.getEmail(), 
				user.getPassword(), 
				user.getRole().getGrantedAuthorities(), 
				true, true, true, 
				user.getEnabled());
	}

}
