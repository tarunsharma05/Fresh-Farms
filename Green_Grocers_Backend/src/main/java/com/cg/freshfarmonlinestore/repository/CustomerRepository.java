package com.cg.freshfarmonlinestore.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.freshfarmonlinestore.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	// finds customer by their customer Id
	Optional<Customer> findByCustomerId(long customerId);

	// finds customer by their username
	Optional<Customer> findByUserName(String userName);

	// check for existance of username
	Boolean existsByUserName(String userName);

	// check for existance of email
	Boolean existsByEmail(String email);

	// check for existance by customer Id
	Boolean existsByCustomerId(long customerId);

	// Search by name, email (partial match)
	List<Customer> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(String name, String email);

}
