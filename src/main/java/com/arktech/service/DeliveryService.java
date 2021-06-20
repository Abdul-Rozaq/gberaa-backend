package com.arktech.service;

import java.time.LocalDate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.arktech.Repository.DeliveryRepository;
import com.arktech.dto.ApiResponse;
import com.arktech.dto.DeliveryDetailDto;
import com.arktech.entity.Delivery;
import com.arktech.entity.User;
import com.arktech.exception.AppException;
import com.arktech.mapper.DeliveryDetailMapper;
import com.arktech.mapper.DeliveryMapper;
import com.arktech.util.DeliveryStatus;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryService {
	
	private static final String SUCCESSFUL = "Successful";
	private static final String ERROR_OCCURED = "Error";
	
	private AuthService authService;
	private DeliveryRepository deliveryRepository;
	private DeliveryDetailService deliveryDetailService;
	private UserService userService;
	private DeliveryDetailMapper deliveryDetailMapper;
	private DeliveryMapper deliveryMapper;

	public ApiResponse getAllDeliveries() {
		try {
			var deliveries = deliveryRepository.findAll()
												.stream()
												.map(deliveryMapper::mapToResponse)
												.collect(Collectors.toList());
			
			return new ApiResponse(SUCCESSFUL, null, deliveries);
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse createDelivery(DeliveryDetailDto request) {
		try {
			User user = authService.getCurrentUser();

			if (request.getDeliveryDate() == null) 
				request.setDeliveryDate(LocalDate.now());

//			Delivery delivery = deliveryRepository.save(deliveryMapper.mapToEntity(user, request.getDeliveryType()));
//			deliveryDetailService.save(deliveryDetailMapper.mapToEntity(request, delivery));
			
			return new ApiResponse(SUCCESSFUL, "Delivery successfully created", null);
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse getDelivery(Long deliveryId) {
		try {
			Delivery delivery = fetchDeliveryById(deliveryId);

			return new ApiResponse(SUCCESSFUL, "Delivery found with ID: " + deliveryId, delivery);
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}
	
	public ApiResponse getDeliveryByStatusForRider(DeliveryStatus status) {
		try {
			User rider = authService.getCurrentUser();
			
			var deliveries = deliveryRepository
								.findByRiderAndStatus(rider, status)
								.stream()
								.map(deliveryMapper::mapToResponse)
								.collect(Collectors.toList());
			
			return new ApiResponse(SUCCESSFUL, null, deliveries);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}
	
	public ApiResponse getDeliveryByStatusForUser(DeliveryStatus status) {
		try {
			User user = authService.getCurrentUser();
			
			var deliveries = deliveryRepository
								.findByUserAndStatus(user, status)
								.stream()
								.map(deliveryMapper::mapToResponse)
								.collect(Collectors.toList());
			
			return new ApiResponse(SUCCESSFUL, null, deliveries);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse getAllDeliveriesForUser() {
		try {
			User user = authService.getCurrentUser();
			
			var deliveries = deliveryRepository.findByUser(user)
												.stream()
												.map(deliveryMapper::mapToResponse)
												.collect(Collectors.toList());
			
			return new ApiResponse(SUCCESSFUL, null, deliveries);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse getAllDeliveriesForRider() {
		try {
			User user = authService.getCurrentUser();
			
			var deliveries = deliveryRepository.findByRider(user)
												.stream()
												.map(deliveryMapper::mapToResponse)
												.collect(Collectors.toList());
			
			return new ApiResponse(SUCCESSFUL, null, deliveries);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse updateStatus(Long deliveryId) {
		try {
			Delivery delivery = fetchDeliveryById(deliveryId);
			
			if (delivery.getStatus() == DeliveryStatus.pending) 
				delivery.setStatus(DeliveryStatus.picked);
			else 
				delivery.setStatus(DeliveryStatus.delivered);
			
			deliveryRepository.save(delivery);
			
			return new ApiResponse(SUCCESSFUL, "Delivery status updated", delivery);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse addDeliveryPrice(Double price, Long deliveryId) {
		try {
			Delivery delivery = fetchDeliveryById(deliveryId);
			delivery.setPrice(price);
			deliveryRepository.save(delivery);	
			
			return new ApiResponse(SUCCESSFUL, "Delivery price updated", delivery);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}

	public ApiResponse assignDeliveryToRider(Long riderId, Long deliveryId) {
		try {
			User rider = userService.findById(riderId);

			Delivery delivery = fetchDeliveryById(deliveryId);			
			delivery.setRider(rider);
			deliveryRepository.save(delivery);
			
			return new ApiResponse(SUCCESSFUL, "Delivery assigned to " + rider.getLastName(), delivery);
			
		} catch (Exception e) {
			return new ApiResponse(ERROR_OCCURED, e.getLocalizedMessage(), null);
		}
	}
	
	private Delivery fetchDeliveryById(Long id) {
		return deliveryRepository
				.findById(id)
				.orElseThrow(() -> new AppException("Delivery not found with ID: " + id));
	}
}
