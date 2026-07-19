package com.cg.freshfarmonlinestore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.service.CustomerService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@RestController
@RequestMapping("/api/customers") 
@AllArgsConstructor
@CrossOrigin("*")
public class CustomerController {
    
    private CustomerService customerService;
    
    /**
     * Get all customers.
     * This method handles GET requests to "/api/customers".
     * It retrieves a list of all customers and returns them in the response body
     * with an HTTP status of OK.
     * 
     * @return ResponseEntity containing the list of customers and HTTP status OK
     */
    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){
        List<Customer> customers = customerService.getAllCustomers();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
    /**
     * Get customer by ID.
     * This method handles GET requests to "/api/customers/{customerId}".
     * It retrieves a customer by their ID and returns them in the response body
     * with an HTTP status of OK.
     * 
     * @param customerId The ID of the customer to retrieve
     * @return ResponseEntity containing the customer and HTTP status OK
     */
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") long customerId){
        Customer customer = customerService.getCustomerById(customerId);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    
    /**
     * Update customer by ID.
     * This method handles PUT requests to "/api/customers/{customerId}".
     * It updates the customer with the given ID using the provided customer details,
     * and returns the updated customer in the response body with an HTTP status of OK.
     * 
     * @param customerId The ID of the customer to update
     * @param customer The updated customer details
     * @return ResponseEntity containing the updated customer and HTTP status OK
     */
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") long customerId, @Valid @RequestBody Customer customer){
        Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }
    
    /**
     * Search customers partially by name or email.
     * This method handles GET requests to "/api/customers/search".
     * It searches for customers by a given search term (which can be a part of their name or email)
     * and returns the matching customers in the response body with an HTTP status of OK.
     * 
     * @param searchTerm The search term to use for finding customers
     * @return ResponseEntity containing the list of matching customers and HTTP status OK
     */
    @GetMapping("/search")
    public ResponseEntity<List<Customer>> searchCustomers(@RequestParam String searchTerm) {
        List<Customer> customers = customerService.searchCustomers(searchTerm);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }
    
    
    /**
     * Delete customer by ID.
     * This method handles DELETE requests to "/api/customers/{customerId}".
     * It deletes the customer with the given ID and returns a response message
     * with an HTTP status of OK.
     * 
     * @param customerId The ID of the customer to delete
     * @return ResponseEntity containing the response message and HTTP status OK
     */
    @DeleteMapping("/{customerId}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("customerId") long customerId){
        String response = customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
