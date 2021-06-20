package com.arktech.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.arktech.dto.ApiResponse;
import com.arktech.dto.DeliveryDetailDto;
import com.arktech.service.DeliveryService;
import com.arktech.util.DeliveryStatus;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController {

	private DeliveryService deliveryService;
	
	@PostMapping
	public ResponseEntity<ApiResponse> createDelivery(@RequestBody DeliveryDetailDto request) {
		System.out.println("Processing request");
		var response = deliveryService.createDelivery(request);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse> getAllDeliveries() {
		var deliveries = deliveryService.getAllDeliveries();
		return new ResponseEntity<>(deliveries, HttpStatus.OK); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse> getDelivery(@PathVariable("id") Long deliveryId) {
		ApiResponse response = deliveryService.getDelivery(deliveryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/for-user")
	public ResponseEntity<ApiResponse> getDeliveriesForUser() {
		var deliveries = deliveryService.getAllDeliveriesForUser();
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-user/status")
	public ResponseEntity<ApiResponse> getDeliveriesForUserByStatus(@RequestParam DeliveryStatus status) {
		var deliveries = deliveryService.getDeliveryByStatusForUser(status);
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-rider")
	public ResponseEntity<ApiResponse> getDeliveriesForRider() {
		var deliveries = deliveryService.getAllDeliveriesForRider();
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-rider/status")
	public ResponseEntity<ApiResponse> getDeliveriesForRiderByStatus(@RequestParam DeliveryStatus status) {
		var deliveries = deliveryService.getDeliveryByStatusForRider(status);
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<ApiResponse> updateDeliveryStatus(@PathVariable("id") Long deliveryId) {
		var response = deliveryService.updateStatus(deliveryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/review")
	public ResponseEntity<ApiResponse> addDeliveryPrice(@RequestParam Double price, @PathVariable("id") Long deliveryId) {
		var response = deliveryService.addDeliveryPrice(price, deliveryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/assign")
	public ResponseEntity<ApiResponse> assignRiderToDelivery(@RequestParam Long rider, @PathVariable("id") Long deliveryId) {
		var response = deliveryService.assignDeliveryToRider(rider, deliveryId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
