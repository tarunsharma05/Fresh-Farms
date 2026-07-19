package com.cg.freshfarmonlinestore.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payment_id")
	private long paymentId;
	
	@Column(name = "payment_mode")
	private String paymentMode;
	
	@Column(name = "card_number")
	private String cardNumber;
	
	@Column(name = "name_on_card")
	private String nameOnCard;
	
	@Column(name = "expiry")
	private String expiry;
	
	@Column(name = "cvv")
	private String cvv;
	
	@Column(name = "upi_id")
	private String upiId;
	
	@Column(name = "payment_date")
	private LocalDate paymentDate;
	
	@Column(name = "payment_time")
	private LocalTime paymentTime;
	
	@Column(name = "payment_status")
	private String paymentStatus;
	
	@Column(name = "item_count")
	private int itemCount;

	@Column(name = "total_amount")
	private double totalAmount;

	@JsonIgnore
	@OneToOne
	private Order order;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;
}