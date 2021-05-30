package com.arktech.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "delivery_details")
public class DeliveryDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "delivery", referencedColumnName = "id")
	private Delivery delivery;
	
	@Column(name = "delivery_date")
	private LocalDate deliveryDate;
	private String pickUpAddress;
	private String pickUpPhoneNumber;
	private String receiverName;
	private String receiverAddress;
	private String receiverPhoneNumber;
	private String additionalInfo;
}
