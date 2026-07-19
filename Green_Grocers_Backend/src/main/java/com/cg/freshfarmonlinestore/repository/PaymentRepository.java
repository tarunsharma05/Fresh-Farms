package com.cg.freshfarmonlinestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	// Find payments by payment status
	List<Payment> findByPaymentStatus(String paymentStatus);

	// Find payments by customer
	List<Payment> findByCustomer(Customer customer);
}
