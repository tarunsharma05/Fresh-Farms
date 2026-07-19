package com.cg.freshfarmonlinestore.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "banks")
public class Bank {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bank_id")
	private long bankId;
	
	@Column(name = "name")
	private String name;

	@Column(name = "bank_name")
	private String bankName;

	@Column(name = "upi_id")
	private String upiId;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "balance")
	private double balance;

}
