package com.arktech.service;

import org.springframework.stereotype.Service;

import com.arktech.Repository.DeliveryDetailRepository;
import com.arktech.Repository.DeliveryRepository;
import com.arktech.entity.Delivery;
import com.arktech.entity.DeliveryDetail;
import com.arktech.exception.AppException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeliveryDetailService {
	
	private DeliveryDetailRepository deliveryDetailRepository;
	private DeliveryRepository deliveryRepository;

	public DeliveryDetail getDetails(Long deliveryId) {
		Delivery delivery = deliveryRepository
				.findById(deliveryId)
				.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));
		
		return deliveryDetailRepository
							.findByDelivery(delivery)
							.orElseThrow(() -> new AppException("Delivery not found with ID: " + deliveryId));

	}

}
