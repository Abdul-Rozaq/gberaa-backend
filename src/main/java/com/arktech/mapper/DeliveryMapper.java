package com.arktech.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.arktech.dto.DeliveryResponse;
import com.arktech.entity.Delivery;
import com.arktech.entity.User;
import com.arktech.util.DeliveryStatus;
import com.arktech.util.DeliveryType;

@Mapper(componentModel = "spring")
public abstract class DeliveryMapper {
	
	@Mapping(target = "user", source = "user")
	@Mapping(target = "status", expression = "java(getStatus())")
	@Mapping(target = "deliveryType", expression = "java(getType(deliveryType))")
	@Mapping(target ="createdDate", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "price", ignore = true)
	@Mapping(target = "rider", ignore = true)
	public abstract Delivery mapToEntity(User user, DeliveryType deliveryType);
	
	@Mapping(target = "userName", source = "delivery.user.lastName")
	@Mapping(target = "userPhoneNumber", source = "delivery.user.phone")
	@Mapping(target = "riderName", source = "delivery.rider.lastName")
	@Mapping(target = "riderPhoneNumber", source = "delivery.rider.phone")
	@Mapping(target = "type", source = "delivery.deliveryType")
	public abstract DeliveryResponse mapToResponse(Delivery delivery);
		
	DeliveryType getType(DeliveryType deliveryType) {
		DeliveryType getType;
		
		if (DeliveryType.scheduled.equals(deliveryType)) {
			getType = DeliveryType.scheduled;
		} else {
			getType = DeliveryType.ondemand;
		}
		
		return getType;
	}
	
	DeliveryStatus getStatus() {
		return DeliveryStatus.pending;
	}
}
