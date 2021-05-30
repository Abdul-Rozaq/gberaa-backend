package com.arktech.dto;

import javax.validation.constraints.NotBlank;

import com.arktech.util.AppUserRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {

	private String firstName;
	@NotBlank
	private String lastName;
	@NotBlank
	private String email;
	@NotBlank
	private String phone;
	@NotBlank
	private String password;
	private String address;
	private AppUserRole role;
}
