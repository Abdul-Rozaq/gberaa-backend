package com.arktech.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arktech.entity.Delivery;
import com.arktech.entity.DeliveryDetail;

@Repository
public interface DeliveryDetailRepository extends JpaRepository<DeliveryDetail, Long> {
	Optional<DeliveryDetail> findByDelivery(Delivery delivery);
}
