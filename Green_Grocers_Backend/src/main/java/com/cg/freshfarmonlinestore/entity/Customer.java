package com.cg.freshfarmonlinestore.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "customer") 
public class Customer extends User {

	@Column(name = "customer_id")
	private long customerId; 

	@NotNull(message = "Phone number cannot be null")
	@Column(name = "phone", nullable = false, unique = true)
	private Long phone;

	@JsonIgnoreProperties("customer")
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Address> addresses = new ArrayList<>();

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "cart_id", referencedColumnName = "cart_id")
	private Cart cart;

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Order> orders = new ArrayList<>();
	
	@OneToMany(mappedBy = "customer",  cascade = CascadeType.ALL , orphanRemoval = true)
	private List<Payment> payments = new ArrayList<>(); 

}
