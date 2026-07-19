package com.cg.freshfarmonlinestore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.service.CustomerService;

// Unit test class for CustomerController
class CustomerControllerUnitTest {

    // Mocking the CustomerService to isolate the controller's functionality during testing.
    @Mock
    private CustomerService customerService;

    // Injecting mocked dependencies into the CustomerController.
    @InjectMocks
    private CustomerController customerController;

    // Sample customer data for testing.
    private Customer customer;
    private List<Customer> customerList;

    // Method to set up initial conditions before each test case.
    @BeforeEach
    void setUp() {
        // Initializing Mockito annotations for mocking dependencies.
        MockitoAnnotations.openMocks(this);

        // Creating a sample customer for testing purposes.
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");

        // Creating a list containing the sample customer.
        customerList = Arrays.asList(customer);
    }

    /**
     * Test case for retrieving all customers.
     * Verifies that the controller returns the correct list of customers.
     */
    @Test
    void testGetAllCustomers() {
        // Mocking the behavior of customerService.getAllCustomers().
        when(customerService.getAllCustomers()).thenReturn(customerList);

        // Invoking the getAllCustomers method of the customerController.
        ResponseEntity<List<Customer>> responseEntity = customerController.getAllCustomers();

        // Asserting that the response status is OK and the body matches the expected list of customers.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(customerList);
    }

    /**
     * Test case for retrieving a customer by ID.
     * Verifies that the controller returns the correct customer for the given ID.
     */
    @Test
    void testGetCustomerById() {
        // Mocking the behavior of customerService.getCustomerById().
        when(customerService.getCustomerById(1L)).thenReturn(customer);

        // Invoking the getCustomerById method of the customerController.
        ResponseEntity<Customer> responseEntity = customerController.getCustomerById(1L);

        // Asserting that the response status is OK and the body matches the expected customer.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(customer);
    }

    /**
     * Test case for updating a customer.
     * Verifies that the controller updates the customer and returns the updated customer.
     */
    @Test
    void testUpdateCustomer() {
        // Mocking the behavior of customerService.updateCustomer().
        when(customerService.updateCustomer(eq(1L), any(Customer.class))).thenReturn(customer);

        // Invoking the updateCustomer method of the customerController.
        ResponseEntity<Customer> responseEntity = customerController.updateCustomer(1L, customer);

        // Asserting that the response status is OK and the body matches the updated customer.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(customer);
    }

    /**
     * Test case for searching customers.
     * Verifies that the controller returns the correct list of customers matching the search term.
     */
    @Test
    void testSearchCustomers() {
        // Mocking the behavior of customerService.searchCustomers().
        when(customerService.searchCustomers("John")).thenReturn(customerList);

        // Invoking the searchCustomers method of the customerController.
        ResponseEntity<List<Customer>> responseEntity = customerController.searchCustomers("John");

        // Asserting that the response status is OK and the body matches the expected list of customers.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo(customerList);
    }

    /**
     * Test case for deleting a customer.
     * Verifies that the controller deletes the customer and returns the correct response message.
     */
    @Test
    void testDeleteCustomer() {
        // Mocking the behavior of customerService.deleteCustomer().
        when(customerService.deleteCustomer(1L)).thenReturn("customer with id 1 is deleted successfully!");

        // Invoking the deleteCustomer method of the customerController.
        ResponseEntity<String> responseEntity = customerController.deleteCustomer(1L);

        // Asserting that the response status is OK and the body matches the expected response message.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEqualTo("customer with id 1 is deleted successfully!");
    }
    


    /**
     * Test case for searching customers with no results.
     * Verifies that the controller returns an empty list when no customers match the search term.
     */
    @Test
    void testSearchCustomers_NoResults() {
        // Mocking the behavior of customerService.searchCustomers() to return an empty list.
        when(customerService.searchCustomers("NonExistent")).thenReturn(Arrays.asList());

        // Invoking the searchCustomers method of the customerController.
        ResponseEntity<List<Customer>> responseEntity = customerController.searchCustomers("NonExistent");

        // Asserting that the response status is OK and the body is an empty list.
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isEmpty();
    }


}
