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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.arktech.util.TransactionStatus;
import com.arktech.util.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "transactions")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private User user;
	
	@OneToOne
	@JoinColumn(name = "delivery")
	private Delivery delivery;

	private String paymentId;
	private Double amount;
	private LocalDateTime date;
	
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	
	@Enumerated(EnumType.STRING)
	private TransactionStatus status;
}
