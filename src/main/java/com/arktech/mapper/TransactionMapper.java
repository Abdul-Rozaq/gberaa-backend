package com.arktech.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.arktech.dto.FundWalletRequest;
import com.arktech.dto.TransactionResponse;
import com.arktech.entity.Delivery;
import com.arktech.entity.Transaction;
import com.arktech.entity.User;
import com.arktech.util.TransactionStatus;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

	@Mapping(target = "id", source = "transaction.id")
	@Mapping(target = "paymentId", source = "transaction.paymentId")
	@Mapping(target = "amount", source = "transaction.amount")
	@Mapping(target = "status", source = "transaction.status")
	@Mapping(target = "paymentDate", source = "transaction.date")
	@Mapping(target = "type", source = "transaction.type")
	@Mapping(target = "userEmail", source = "transaction.user.email")
	@Mapping(target = "userPhoneNumber", source = "transaction.user.phone")
	@Mapping(target = "userLastname", source = "transaction.user.lastName")		
	@Mapping(target = "deliveryId", source = "transaction.delivery.id")
	@Mapping(target = "deliveryType", source = "transaction.delivery.deliveryType")
	@Mapping(target = "riderEmail", source = "transaction.delivery.rider.email")
	@Mapping(target = "riderPhoneNumber", source = "transaction.delivery.rider.phone")
	@Mapping(target = "riderLastname", source = "transaction.delivery.rider.lastName")
	TransactionResponse mapToResponse(Transaction transaction);
	
	@Mapping(target = "user", source = "user")
	@Mapping(target = "delivery", ignore = true)
	@Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
	Transaction mapToWallet(FundWalletRequest request, User user);
	
	@Mapping(target = "status", source = "declined")
	@Mapping(target = "delivery", source = "delivery")
	@Mapping(target = "amount", source = "delivery.price")
	@Mapping(target = "date", expression = "java(java.time.LocalDateTime.now())")
	@Mapping(target = "type", ignore = true)
	@Mapping(target = "paymentId", ignore = true)
	@Mapping(target = "id", ignore = true)
	Transaction mapToPayment(Delivery delivery, User user, TransactionStatus declined);
}
