package com.arktech.controller;

import java.util.List;

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

import com.arktech.dto.DeliveryResponse;
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
	public ResponseEntity<Void> createDelivery(@RequestBody DeliveryDetailDto request) {
		deliveryService.createDelivery(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<DeliveryResponse>> getAllDeliveries() {
		List<DeliveryResponse> deliveries = deliveryService.getAllDeliveries();
		return new ResponseEntity<>(deliveries, HttpStatus.OK); 
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<DeliveryResponse> getDelivery(@PathVariable("id") Long deliveryId) {
		DeliveryResponse delivery = deliveryService.getDelivery(deliveryId);
		return new ResponseEntity<>(delivery, HttpStatus.OK);
	}
	
	@GetMapping("/for-user")
	public ResponseEntity<List<DeliveryResponse>> getDeliveriesForUser() {
		List<DeliveryResponse> deliveries = deliveryService.getAllDeliveriesForUser();
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-user/status")
	public ResponseEntity<List<DeliveryResponse>> getDeliveriesForUserByStatus(@RequestParam DeliveryStatus status) {
		List<DeliveryResponse> deliveries = deliveryService.getDeliveryByStatusForUser(status);
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-rider")
	public ResponseEntity<List<DeliveryResponse>> getDeliveriesForRider() {
		List<DeliveryResponse> deliveries = deliveryService.getAllDeliveriesForRider();
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@GetMapping("/for-rider/status")
	public ResponseEntity<List<DeliveryResponse>> getDeliveriesForRiderByStatus(@RequestParam DeliveryStatus status) {
		List<DeliveryResponse> deliveries = deliveryService.getDeliveryByStatusForRider(status);
		return new ResponseEntity<>(deliveries, HttpStatus.OK);
	}
	
	@PutMapping("/{id}/status")
	public ResponseEntity<Void> updateDeliveryStatus(@PathVariable("id") Long deliveryId) {
		deliveryService.updateStatus(deliveryId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}/review")
	public ResponseEntity<Void> addDeliveryPrice(@RequestParam Double price, @PathVariable("id") Long deliveryId) {
		deliveryService.addDeliveryPrice(price, deliveryId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@PutMapping("/{id}/assign")
	public ResponseEntity<Void> assignRiderToDelivery(@RequestParam Long rider, @PathVariable("id") Long deliveryId) {
		deliveryService.assignDeliveryToRider(rider, deliveryId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
