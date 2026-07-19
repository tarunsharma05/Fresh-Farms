package com.cg.freshfarmonlinestore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.service.CustomerService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository; 

    /**
     * Retrieves all customers.
     *
     * @return a list of all customers
     */
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    /**
     * Retrieves a customer by their ID.
     *
     * @param customerId the ID of the customer to retrieve
     * @return the customer with the given ID
     * @throws ResourceNotFoundException if the customer is not found
     */
    
    @Override 
    public Customer getCustomerById(long customerId) {
        return customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
    }

    /**
     * Updates a customer's details.
     *
     * @param customerId the ID of the customer to update
     * @param customer   the new customer details
     * @return the updated customer
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public Customer updateCustomer(long customerId, Customer customer) {
        Customer existingCustomer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        existingCustomer.setName(customer.getName());
        existingCustomer.setPhone(customer.getPhone());
        existingCustomer.setEmail(customer.getEmail());

        return customerRepository.save(existingCustomer);
    }

    /**
     * Deletes a customer by their ID.
     *
     * @param customerId the ID of the customer to delete
     * @return a message indicating the deletion status
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public String deleteCustomer(long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        // Clear associated entities
        clearAssociatedEntities(customer);

        // Optionally delete the user record if it is necessary
        customerRepository.deleteById(customer.getUserId());

        return "Customer with ID " + customerId + " is deleted successfully!";
    }

    /**
     * Clears associated entities of a customer before deletion.
     *
     * @param customer the customer whose associated entities need to be cleared
     */
    private void clearAssociatedEntities(Customer customer) {
        customer.getAddresses().clear();
        if (customer.getCart() != null) {
            customer.getCart().getItems().clear();
            customer.getCart().setCustomer(null);
        }
        customer.getOrders().forEach(order -> {
            if (order.getPayment() != null) {
                order.setPayment(null);
            }
            order.setCustomer(null);
        });
        customer.getOrders().clear();
        customer.getPayments().clear();
    }
    
    /**
     * Searches for customers based on a search term. This method performs a partial match search
     * on the customer's name, email.
     *
     * @param searchTerm the search term to match against customer names, email's.
     * @return a list of customers matching the search term
     */
    @Override
    public List<Customer> searchCustomers(String searchTerm) {
        return customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(searchTerm, searchTerm);
    }

}
