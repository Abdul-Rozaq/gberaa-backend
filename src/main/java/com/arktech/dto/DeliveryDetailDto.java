package com.arktech.dto;

import java.time.LocalDate;

import com.arktech.util.DeliveryType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class DeliveryDetailDto {

    private String pickUpAddress;
    private String pickUpPhoneNumber;
    private String receiverAddress;
    private String receiverName;
    private String receiverPhoneNumber;
    private LocalDate deliveryDate;
    private String additionalInfo;
    private DeliveryType deliveryType;
}
