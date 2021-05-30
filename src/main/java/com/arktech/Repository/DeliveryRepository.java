package com.arktech.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arktech.entity.Delivery;
import com.arktech.entity.User;
import com.arktech.util.DeliveryStatus;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
	List<Delivery> findByUser(User user);
	List<Delivery> findByUserAndStatus(User user, DeliveryStatus status);
			
	List<Delivery> findByRider(User rider);
	List<Delivery> findByRiderAndStatus(User rider, DeliveryStatus status);
}
