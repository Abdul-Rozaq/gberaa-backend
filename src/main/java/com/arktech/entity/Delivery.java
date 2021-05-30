package com.arktech.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.arktech.util.DeliveryStatus;
import com.arktech.util.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "deliveries")
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "rider", referencedColumnName = "id")
	private User rider;
	
	@Enumerated(EnumType.STRING)
	private DeliveryType deliveryType;

	@Enumerated(EnumType.STRING)
	private DeliveryStatus status;
	
	private Double price;
	private LocalDateTime createdDate;
}
