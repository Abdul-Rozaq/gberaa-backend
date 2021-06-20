package com.arktech.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arktech.entity.Delivery;
import com.arktech.entity.Transaction;
import com.arktech.entity.User;
import com.arktech.util.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

	List<Transaction> findByUser(User user);
	List<Transaction> findByType(TransactionType type);
	
	Transaction findByDelivery(Delivery delivery);
}
