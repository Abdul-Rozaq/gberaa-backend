package com.arktech.dto;

import java.time.Instant;

import com.arktech.util.AppUserRole;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserDto {

	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private AppUserRole role;
	private Instant createdDate;
	private Integer tasks;
	private Integer delivered;
	private Integer pending;
	
	public UserDto(Long id, String firstName, String lastName, String email, String phone, String address,
			AppUserRole role, Instant createdDate, Integer tasks, Integer delivered, Integer pending) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.role = role;
		this.createdDate = createdDate;
		this.tasks = tasks;
		this.delivered = delivered;
		this.pending = tasks - delivered;
	}
	
	
}
