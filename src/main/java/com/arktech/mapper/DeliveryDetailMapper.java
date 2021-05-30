package com.arktech.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.arktech.dto.DeliveryDetailDto;
import com.arktech.entity.Delivery;
import com.arktech.entity.DeliveryDetail;

@Mapper(componentModel = "spring")
public interface DeliveryDetailMapper {

	@Mapping(target = "delivery", source = "delivery")
	@Mapping(target = "deliveryDate", source = "request.deliveryDate")
	@Mapping(target = "pickUpAddress", source = "request.pickUpAddress")
	@Mapping(target = "pickUpPhoneNumber", source = "request.pickUpPhoneNumber")
	@Mapping(target = "receiverName", source = "request.receiverName")
	@Mapping(target = "receiverAddress", source = "request.receiverAddress")
	@Mapping(target = "receiverPhoneNumber", source = "request.receiverPhoneNumber")
	@Mapping(target = "additionalInfo", source = "request.additionalInfo")
	DeliveryDetail mapToEntity(DeliveryDetailDto request, Delivery delivery);
	
	@InheritInverseConfiguration
	@Mapping(target = "deliveryType", ignore = true)
	DeliveryDetailDto mapToDto(DeliveryDetail deliveryDetail);
}
