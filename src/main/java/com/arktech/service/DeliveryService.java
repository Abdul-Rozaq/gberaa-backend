package com.arktech.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arktech.Repository.DeliveryDetailRepository;
import com.arktech.Repository.DeliveryRepository;
import com.arktech.dto.DeliveryDetailDto;
import com.arktech.dto.DeliveryResponse;
import com.arktech.entity.Delivery;
import com.arktech.entity.User;
import com.arktech.exception.AppException;
import com.arktech.mapper.DeliveryDetailMapper;
import com.arktech.mapper.DeliveryMapper;
import com.arktech.util.AppUserRole;
import com.arktech.util.DeliveryStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryService {
	
	private AuthService authService;
	private DeliveryRepository deliveryRepository;
	private DeliveryDetailRepository deliveryDetailRepository;
	private UserService userService;
	private DeliveryDetailMapper deliveryDetailMapper;
	private DeliveryMapper deliveryMapper;

	public List<DeliveryResponse> getAllDeliveries() {
		return deliveryRepository.findAll()
				.stream()
				.map(deliveryMapper::mapToResponse)
				.collect(Collectors.toList());
	}

	public void createDelivery(DeliveryDetailDto request) {
		User user = authService.getCurrentUser();
		
		if (request.getDeliveryDate() == null) 
			 request.setDeliveryDate(LocalDate.now());
		 
		Delivery delivery = deliveryRepository.save(deliveryMapper.mapToEntity(user, request.getDeliveryType()));
		deliveryDetailRepository.save(deliveryDetailMapper.mapToEntity(request, delivery));
	}

	public DeliveryResponse getDelivery(Long deliveryId) {
		Delivery delivery = deliveryRepository
									.findById(deliveryId)
									.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
		
		return deliveryMapper.mapToResponse(delivery);
	}
	
	public List<DeliveryResponse> getDeliveryByStatusForRider(DeliveryStatus status) {
		User rider = authService.getCurrentUser();
		return deliveryRepository
				.findByRiderAndStatus(rider, status)
				.stream()
				.map(deliveryMapper::mapToResponse)
				.collect(Collectors.toList());
	}
	
	public List<DeliveryResponse> getDeliveryByStatusForUser(DeliveryStatus status) {
		User user = authService.getCurrentUser();
		return deliveryRepository
				.findByUserAndStatus(user, status)
				.stream()
				.map(deliveryMapper::mapToResponse)
				.collect(Collectors.toList());
	}

	public List<DeliveryResponse> getAllDeliveriesForUser() {
		User user = authService.getCurrentUser();
		
		return deliveryRepository.findByUser(user)
				.stream()
				.map(deliveryMapper::mapToResponse)
				.collect(Collectors.toList());
	}

	public List<DeliveryResponse> getAllDeliveriesForRider() {
		User user = authService.getCurrentUser();
		
		return deliveryRepository.findByRider(user)
				.stream()
				.map(deliveryMapper::mapToResponse)
				.collect(Collectors.toList());
	}

	public void updateStatus(Long deliveryId) {
		Delivery delivery = deliveryRepository
				.findById(deliveryId)
				.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
		
		if (delivery.getStatus() == DeliveryStatus.pending) {
			delivery.setStatus(DeliveryStatus.picked);
		} else {
			delivery.setStatus(DeliveryStatus.delivered);
		}
		
		deliveryRepository.save(delivery);
	}

	public void addDeliveryPrice(Double price, Long deliveryId) {
		Delivery delivery = deliveryRepository
				.findById(deliveryId)
				.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
		
		delivery.setPrice(price);
		deliveryRepository.save(delivery);
	}

	public void assignDeliveryToRider(Long riderId, Long deliveryId) {
		User rider = userService.findById(riderId);
		
		if (rider.getRole() != AppUserRole.RIDER) 
			throw new AppException("Delivery can only be assigned to a rider");
		
		Delivery delivery = deliveryRepository
				.findById(deliveryId)
				.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
		
		delivery.setRider(rider);
		deliveryRepository.save(delivery);
	}

}
