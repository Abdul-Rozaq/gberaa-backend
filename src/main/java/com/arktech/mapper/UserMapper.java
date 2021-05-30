package com.arktech.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.arktech.Repository.DeliveryRepository;
import com.arktech.dto.UserDto;
import com.arktech.entity.User;
import com.arktech.util.DeliveryStatus;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Mapper(componentModel = "spring")
public abstract class UserMapper {
	
	private DeliveryRepository deliveryRepository;
	
	@Mapping(target = "tasks", constant = "0")
	@Mapping(target = "delivered", constant = "0")
	@Mapping(target = "pending", constant = "0")
	public abstract UserDto mapToDto(User user);
	
	@Mapping(target = "tasks", expression="java(countTasks(rider))")
	@Mapping(target = "delivered", expression="java(countTasksByPending(rider))")
	@Mapping(target = "pending", ignore = true)
	public abstract UserDto mapRiderToDto(User rider);
	
	Integer countTasks(User rider) {
		return deliveryRepository.findByRider(rider).size();
	}
	
	Integer countTasksByPending(User rider) {
		return deliveryRepository.findByRiderAndStatus(rider, DeliveryStatus.delivered).size();
	}
}
