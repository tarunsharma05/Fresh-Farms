package com.cg.freshfarmonlinestore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name = "address_id")
	private long addressId; 

	@NotBlank(message = "House number cannot be blank")
	@Size(max = 10, message = "House number cannot exceed 10 characters")
	@Column(name = "house_no", nullable = false)
	private String houseNo;

	@NotBlank(message = "Landmark cannot be blank")
	@Size(max = 50, message = "Landmark cannot exceed 50 characters")
	@Column(name = "landmark", nullable = false)
	private String landmark;

	@NotBlank(message = "City cannot be blank")
	@Size(max = 50, message = "City cannot exceed 50 characters")
	@Column(name = "city", nullable = false)
	private String city;

	@NotBlank(message = "State cannot be blank")
	@Size(max = 50, message = "State cannot exceed 50 characters")
	@Column(name = "state", nullable = false)
	private String state;

	@NotBlank(message = "Country cannot be blank")
	@Size(max = 50, message = "Country cannot exceed 50 characters")
	@Column(name = "country", nullable = false)
	private String country;

	@NotBlank(message = "Pin code cannot be blank")
	@Pattern(regexp = "^[1-9][0-9]{5}$", message = "Pin code must be a 6-digit number")
	@Column(name = "pin_code", nullable = false)
	private String pinCode;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;
}