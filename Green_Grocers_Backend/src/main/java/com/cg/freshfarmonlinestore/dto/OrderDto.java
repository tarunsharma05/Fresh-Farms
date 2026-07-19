package com.cg.freshfarmonlinestore.dto;
 
import java.time.LocalDate;
import java.util.List;

import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.entity.OrderedItem;
import com.cg.freshfarmonlinestore.entity.Payment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	
	private long orderId;
	
	private LocalDate orderDate;
	
	@NotBlank(message = "Order status is required")
	private String orderStatus;
	
	private Payment payment;
	
	private Address address;
	
	private List<OrderedItem> orderedItems;
}