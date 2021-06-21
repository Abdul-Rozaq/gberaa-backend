package com.arktech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.service.DeliveryDetailService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/delivery/detail")
public class DeliveryDetailController {

	private DeliveryDetailService deliveryDetailService;
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getDeliveryDetail(@PathVariable("id") Long deliveryId) {
		ApiResponse response = deliveryDetailService.getDetails(deliveryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
