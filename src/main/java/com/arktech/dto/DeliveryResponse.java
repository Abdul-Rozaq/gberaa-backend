package com.arktech.dto;

import java.time.LocalDateTime;

import com.arktech.util.DeliveryStatus;
import com.arktech.util.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryResponse {

	private Long id;
	private String userName;
	private String userPhoneNumber;
	private String riderName;
	private String riderPhoneNumber;
	private Double price;
	private LocalDateTime createdDate;
	private DeliveryType type;
	private DeliveryStatus status;
}
