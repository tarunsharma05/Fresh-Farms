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
 
import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.AddressRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
 
/**
* Unit tests for the AddressServiceImpl class.
*/
class AddressServiceImplTest {
 
	@Mock
    private AddressRepository addressRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private AddressServiceImpl addressService;

    /**
     * Sets up the mock objects before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test case for retrieving all addresses.
     * Verifies that the service returns the correct list of addresses.
     */
    @Test
    void testGetAllAddresses() {
        // Prepare a mock list of addresses
        List<Address> addresses = new ArrayList<>();
        
        // Mock the repository call to return the mock list
        when(addressRepository.findAll()).thenReturn(addresses);
        
        // Call the service method
        List<Address> result = addressService.getAllAddresses();
        
        // Verify that the service returns the expected list
        assertThat(result).isEqualTo(addresses);
        
        // Verify that the repository's findAll method was called once
        verify(addressRepository, times(1)).findAll();
    }

    /**
     * Test case for retrieving an address by ID, when the address is found.
     * Verifies that the service returns the correct address.
     */
    @Test
    void testGetAddressById_Success() {
        // Prepare a mock address
        Address address = new Address();
        
        // Mock the repository call to return the mock address
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        
        // Call the service method
        Address result = addressService.getAddressById(1L);
        
        // Verify that the service returns the expected address
        assertThat(result).isEqualTo(address);
        
        // Verify that the repository's findById method was called once
        verify(addressRepository, times(1)).findById(1L);
    }

    /**
     * Test case for retrieving an address by ID, when the address is not found.
     * Verifies that the service throws ResourceNotFoundException.
     */
    @Test
    void testGetAddressById_NotFound() {
        // Mock the repository call to return an empty result
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Verify that the service throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressById(1L));
        
        // Verify that the repository's findById method was called once
        verify(addressRepository, times(1)).findById(1L);
    }

    /**
     * Test case for adding an address, when the associated customer is found.
     * Verifies that the service correctly adds the address.
     */
    @Test
    void testAddAddress_Success() {
        // Prepare a mock customer and address
        Customer customer = new Customer();
        Address address = new Address();
        Address savedAddress = new Address();
        
        // Mock the repository call to return the mock customer
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        
        // Mock the repository call to save the address
        when(addressRepository.save(address)).thenReturn(savedAddress);
        
        // Call the service method
        Address result = addressService.addAddress(address, 1L);
        
        // Verify that the service returns the saved address
        assertThat(result).isEqualTo(savedAddress);
        
        // Verify that the repositories' methods were called correctly
        verify(customerRepository, times(1)).findByCustomerId(1L);
        verify(addressRepository, times(1)).save(address);
        verify(customerRepository, times(1)).save(customer);
    }

    /**
     * Test case for adding an address, when the associated customer is not found.
     * Verifies that the service throws ResourceNotFoundException.
     */
    @Test
    void testAddAddress_CustomerNotFound() {
        // Prepare a mock address
        Address address = new Address();
        
        // Mock the repository call to return an empty result
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
        
        // Verify that the service throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> addressService.addAddress(address, 1L));
        
        // Verify that the repository's findByCustomerId method was called once
        verify(customerRepository, times(1)).findByCustomerId(1L);
        
        // Verify that the repository's save method was not called
        verify(addressRepository, times(0)).save(address);
    }

    /**
     * Test case for updating an address, when the address is found.
     * Verifies that the service correctly updates the address.
     */
    @Test
    void testUpdateAddress_Success() {
        // Prepare mock existing and updated addresses
        Address existingAddress = new Address();
        Address updatedAddress = new Address();
        
        // Mock the repository call to return the existing address
        when(addressRepository.findById(1L)).thenReturn(Optional.of(existingAddress));
        
        // Mock the repository call to save the updated address
        when(addressRepository.save(existingAddress)).thenReturn(existingAddress);
        
        // Call the service method
        Address result = addressService.updateAddress(1L, updatedAddress);
        
        // Verify that the service returns the updated address
        assertThat(result).isEqualTo(existingAddress);
        
        // Verify that the repositories' methods were called correctly
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).save(existingAddress);
    }

    /**
     * Test case for updating an address, when the address is not found.
     * Verifies that the service throws ResourceNotFoundException.
     */
    @Test
    void testUpdateAddress_NotFound() {
        // Prepare a mock address
        Address address = new Address();
        
        // Mock the repository call to return an empty result
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Verify that the service throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> addressService.updateAddress(1L, address));
        
        // Verify that the repository's findById method was called once
        verify(addressRepository, times(1)).findById(1L);
    }

    /**
     * Test case for deleting an address, when the address is found.
     * Verifies that the service correctly deletes the address.
     */
    @Test
    void testDeleteAddress_Success() {
        // Prepare a mock address
        Address address = new Address();
        
        // Mock the repository call to return the address
        when(addressRepository.findById(1L)).thenReturn(Optional.of(address));
        
        // Call the service method
        String result = addressService.deleteAddress(1L);
        
        // Verify that the service returns the expected result
        assertThat(result).isEqualTo("Address deleted with ID: 1");
        
        // Verify that the repositories' methods were called correctly
        verify(addressRepository, times(1)).findById(1L);
        verify(addressRepository, times(1)).delete(address);
    }

    /**
     * Test case for deleting an address, when the address is not found.
     * Verifies that the service throws ResourceNotFoundException.
     */
    @Test
    void testDeleteAddress_NotFound() {
        // Mock the repository call to return an empty result
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());
        
        // Verify that the service throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> addressService.deleteAddress(1L));
        
        // Verify that the repository's findById method was called once
        verify(addressRepository, times(1)).findById(1L);
    }

    /**
     * Test case for retrieving addresses by customer ID, when the customer is found.
     * Verifies that the service returns the correct list of addresses.
     */
    @Test
    void testGetAddressesByCustomerId_Success() {
        // Prepare a mock customer with a list of addresses
        Customer customer = new Customer();
        List<Address> addresses = new ArrayList<>();
        customer.setAddresses(addresses);
        
        // Mock the repository call to return the customer
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        
        // Call the service method
        List<Address> result = addressService.getAddressesByCustomerId(1L);
        
        // Verify that the service returns the expected list of addresses
        assertThat(result).isEqualTo(addresses);
        
        // Verify that the repository's findByCustomerId method was called once
        verify(customerRepository, times(1)).findByCustomerId(1L);
    }
    
    /**
     * Test case for retrieving addresses by customer ID, when the customer is not found.
     * Verifies that the service throws ResourceNotFoundException.
     */
    @Test
    void testGetAddressesByCustomerId_CustomerNotFound() {
        // Mock the repository call to return an empty result
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());

        // Verify that the service throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> addressService.getAddressesByCustomerId(1L));

        // Verify that the repository's findByCustomerId method was called once
        verify(customerRepository, times(1)).findByCustomerId(1L);
    }
}
