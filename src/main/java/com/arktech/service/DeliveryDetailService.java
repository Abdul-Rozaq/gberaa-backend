package com.arktech.service;

import org.springframework.stereotype.Service;

import com.arktech.Repository.DeliveryDetailRepository;
import com.arktech.dto.ApiResponse;
import com.arktech.entity.DeliveryDetail;
import com.arktech.exception.AppException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryDetailService {
	
	private DeliveryDetailRepository deliveryDetailRepository;

	public ApiResponse getDetails(Long deliveryId) {
		try {
			var details = deliveryDetailRepository
					.findByDelivery_id(deliveryId)
					.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
			
			return new ApiResponse("Successful", null, details);
			
		} catch (Exception e) {
			return new ApiResponse("Error", e.getLocalizedMessage(), null);
		}
	}
	
	public void save(DeliveryDetail detail) {
		deliveryDetailRepository.save(detail);
	}

}
