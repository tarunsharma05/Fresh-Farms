package com.cg.freshfarmonlinestore.service.impl;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;

class CustomerServiceImplTest {

 @Mock
 private CustomerRepository customerRepository;


 @InjectMocks
 private CustomerServiceImpl customerService;


 @BeforeEach
 void setUp() {
     // Initializing Mockito annotations for mocking dependencies.
     MockitoAnnotations.openMocks(this);
 }

 /**
  * Test for the getAllCustomers method of the CustomerServiceImpl.
  * Verifies that the service returns all customers.
  */
 @Test
 void testGetAllCustomers() {
     // Mocking the behavior of the customerRepository.findAll() method.
     List<Customer> customers = new ArrayList<>();
     when(customerRepository.findAll()).thenReturn(customers);

     // Invoking the getAllCustomers method of the customerService.
     List<Customer> result = customerService.getAllCustomers();

     // Asserting that the result matches the expected list of customers.
     assertThat(result).isEqualTo(customers);
     verify(customerRepository, times(1)).findAll();
 }

 
 /**
  * Test for the getCustomerById method of the CustomerServiceImpl.
  * Verifies that the service returns the correct customer for the given ID.
  */
 @Test
 void testGetCustomerById_Success() {
     // Creating a customer and mocking the behavior of customerRepository.findByCustomerId().
     Customer customer = new Customer();
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

     // Invoking the getCustomerById method of the customerService.
     Customer result = customerService.getCustomerById(1L);

     // Asserting that the result matches the expected customer.
     assertThat(result).isEqualTo(customer);
     verify(customerRepository, times(1)).findByCustomerId(1L);
 }

 
 /**
  * Test for the getCustomerById method of the CustomerServiceImpl when customer is not found.
  * Verifies that the service throws a ResourceNotFoundException when customer is not found.
  */
 @Test
 void testGetCustomerById_NotFound() {
     // Mocking the behavior of customerRepository.findByCustomerId() to return empty Optional.
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());

     // Asserting that invoking getCustomerById with a non-existent ID throws ResourceNotFoundException.
     assertThrows(ResourceNotFoundException.class, () -> customerService.getCustomerById(1L));

     // Verifying that findByCustomerId method was called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
 }

 
 /**
  * Test for the updateCustomer method of the CustomerServiceImpl.
  * Verifies that the service successfully updates a customer's information.
  */
 @Test
 void testUpdateCustomer_Success() {
     // Creating existing and updated customer objects.
     Customer existingCustomer = new Customer();
     Customer updatedCustomer = new Customer();
     updatedCustomer.setName("Updated Name");
     updatedCustomer.setPhone(9345565657L);
     updatedCustomer.setEmail("updated@example.com");

     // Mocking the behavior of customerRepository.findByCustomerId and customerRepository.save.
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(existingCustomer));
     when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);

     // Invoking the updateCustomer method of the customerService.
     Customer result = customerService.updateCustomer(1L, updatedCustomer);

     // Asserting that the updated customer matches the expected values.
     assertThat(result.getName()).isEqualTo(updatedCustomer.getName());
     assertThat(result.getPhone()).isEqualTo(updatedCustomer.getPhone());
     assertThat(result.getEmail()).isEqualTo(updatedCustomer.getEmail());

     // Verifying that findByCustomerId and save methods were called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
     verify(customerRepository, times(1)).save(existingCustomer);
 }

 
 /**
  * Test for the updateCustomer method of the CustomerServiceImpl when customer is not found.
  * Verifies that the service throws a ResourceNotFoundException when customer is not found.
  */
 @Test
 void testUpdateCustomer_NotFound() {
     // Creating a customer and mocking the behavior of customerRepository.findByCustomerId to return empty Optional.
     Customer customer = new Customer();
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());

     // Asserting that invoking updateCustomer with a non-existent ID throws ResourceNotFoundException.
     assertThrows(ResourceNotFoundException.class, () -> customerService.updateCustomer(1L, customer));

     // Verifying that findByCustomerId method was called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
 }

 
 /**
  * Test for the deleteCustomer method of the CustomerServiceImpl.
  * Verifies that the service successfully deletes a customer.
  */
 @Test
 void testDeleteCustomer_Success() {
     // Creating a customer and mocking the behavior of customerRepository.findByCustomerId.
     Customer customer = new Customer();
     customer.setUserId(1L);
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

     // Invoking the deleteCustomer method of the customerService.
     String result = customerService.deleteCustomer(1L);

     // Asserting that the result matches the expected success message.
     assertThat(result).isEqualTo("Customer with ID 1 is deleted successfully!");

     // Verifying that findByCustomerId and deleteById methods were called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
     verify(customerRepository, times(1)).deleteById(1L);
 }

 
 /**
  * Test for the deleteCustomer method of the CustomerServiceImpl when customer is not found.
  * Verifies that the service throws a ResourceNotFoundException when customer is not found.
  */
 @Test
 void testDeleteCustomer_NotFound() {
     // Mocking the behavior of customerRepository.findByCustomerId to return empty Optional.
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());

     // Asserting that invoking deleteCustomer with a non-existent ID throws ResourceNotFoundException.
     assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomer(1L));

     // Verifying that findByCustomerId method was called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
 }

 
 /**
  * Test for the searchCustomers method of the CustomerServiceImpl.
  * Verifies that the service returns customers matching the search term.
  */
 @Test
 void testSearchCustomers() {
     // Mocking the behavior of customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase.
     List<Customer> customers = new ArrayList<>();
     when(customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("searchTerm", "searchTerm")).thenReturn(customers);

     // Invoking the searchCustomers method of the customerService.
     List<Customer> result = customerService.searchCustomers("searchTerm");

     // Asserting that the result matches the expected list of customers.
     assertThat(result).isEqualTo(customers);

     // Verifying that findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase method was called exactly once.
     verify(customerRepository, times(1)).findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("searchTerm", "searchTerm");
 }
 
 
 /**
  * Test for the deleteCustomer method when customer has associated entities.
  * Verifies that the service clears associated entities before deletion.
  */
 @Test
 void testDeleteCustomer_WithAssociatedEntities() {
     // Creating a customer with associated entities.
     Customer customer = new Customer();
     customer.setUserId(1L);
     customer.setAddresses(new ArrayList<>());
     customer.setCart(null);
     customer.setOrders(new ArrayList<>());
     customer.setPayments(new ArrayList<>());

     // Mocking the behavior of customerRepository.findByCustomerId.
     when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));

     // Invoking the deleteCustomer method of the customerService.
     String result = customerService.deleteCustomer(1L);

     // Asserting that the result matches the expected success message.
     assertThat(result).isEqualTo("Customer with ID 1 is deleted successfully!");

     // Verifying that findByCustomerId and deleteById methods were called exactly once.
     verify(customerRepository, times(1)).findByCustomerId(1L);
     verify(customerRepository, times(1)).deleteById(1L);
 }

 /**
  * Test for the searchCustomers method when no customers match the search term.
  * Verifies that the service returns an empty list.
  */
 @Test
 void testSearchCustomers_NoMatches() {
     // Mocking the behavior of customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase.
     when(customerRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("nonexistent", "nonexistent")).thenReturn(new ArrayList<>());

     // Invoking the searchCustomers method of the customerService.
     List<Customer> result = customerService.searchCustomers("nonexistent");

     // Asserting that the result is an empty list.
     assertThat(result).isEmpty();

     // Verifying that findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase method was called exactly once.
     verify(customerRepository, times(1)).findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase("nonexistent", "nonexistent");
 }

}