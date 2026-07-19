package com.cg.freshfarmonlinestore.service;

import java.util.List;

import com.cg.freshfarmonlinestore.entity.Customer;

public interface CustomerService {

	// Get all customers
	List<Customer> getAllCustomers();
	
	// Get customer by Id
	Customer getCustomerById(long customerId);
	
	// Update customer by Id
	Customer updateCustomer(long customerId, Customer customer);
	
	// Delete customer by Id
	String deleteCustomer(long customerId);
	
	// Search customers by name, email (partial match)
    List<Customer> searchCustomers(String searchTerm);

}
