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

import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.service.AddressService;

class AddressControllerUnitTest {

	@Mock
	private AddressService addressService;

	// Injecting mocked dependencies into the AddressController.
	@InjectMocks
	private AddressController addressController;

	private Address address;
	private List<Address> addressList;

	@BeforeEach
	void setUp() {
		// Initializing Mockito annotations for mocking dependencies.
		MockitoAnnotations.openMocks(this);

		// Creating a sample address object for testing.
		address = new Address();
		address.setAddressId(1L);
		address.setCity("City");
		address.setState("State");
		address.setPinCode("12345");

		// Creating a list of addresses for testing.
		addressList = Arrays.asList(address);
	}

	/**
	 * Test for the getAllAddresses method of the AddressController. Verifies that
	 * the controller returns all addresses.
	 */
	@Test
	void testGetAllAddresses() {
		// Mock the addressService to return the address list
		when(addressService.getAllAddresses()).thenReturn(addressList);

		// Call the method under test
		ResponseEntity<List<Address>> responseEntity = addressController.getAllAddresses();

		// Assert that the response status code is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Assert that the response body matches the expected address list
		assertThat(responseEntity.getBody()).isEqualTo(addressList);
	}

	
	/**
	 * Test for the getAddressById method of the AddressController. Verifies that
	 * the controller returns the correct address for the given ID.
	 */
	@Test
	void testGetAddressById() {
		// Mock the addressService to return the address when getAddressById is called
		// with ID 1
		when(addressService.getAddressById(1L)).thenReturn(address);

		// Call the method under test
		ResponseEntity<Address> responseEntity = addressController.getAddressById(1L);

		// Assert that the response status code is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Assert that the response body matches the expected address
		assertThat(responseEntity.getBody()).isEqualTo(address);
	}

	
	/**
	 * Test for the addAddress method of the AddressController. Verifies that the
	 * controller successfully adds an address.
	 */
	@Test
	void testAddAddress() {
		// Mock the addressService to return the added address
		when(addressService.addAddress(any(Address.class), eq(1L))).thenReturn(address);

		// Call the method under test
		ResponseEntity<Address> responseEntity = addressController.addAddress(address, 1L);

		// Assert that the response status code is CREATED
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// Assert that the response body matches the expected address
		assertThat(responseEntity.getBody()).isEqualTo(address);
	}

	
	/**
	 * Test for the updateAddress method of the AddressController. Verifies that the
	 * controller successfully updates an address.
	 */
	@Test
	void testUpdateAddress() {
		// Mock the addressService to return the updated address
		when(addressService.updateAddress(eq(1L), any(Address.class))).thenReturn(address);

		// Call the method under test
		ResponseEntity<Address> responseEntity = addressController.updateAddress(1L, address);

		// Assert that the response status code is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Assert that the response body matches the expected address
		assertThat(responseEntity.getBody()).isEqualTo(address);
	}

	
	/**
	 * Test for the deleteAddress method of the AddressController. Verifies that the
	 * controller successfully deletes an address.
	 */
	@Test
	void testDeleteAddress() {
		// Mock the addressService to return success message upon deletion
		when(addressService.deleteAddress(1L)).thenReturn("Address with id 1 is deleted successfully!");

		// Call the method under test
		ResponseEntity<String> responseEntity = addressController.deleteAddress(1L);

		// Assert that the response status code is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Assert that the response body matches the expected success message
		assertThat(responseEntity.getBody()).isEqualTo("Address with id 1 is deleted successfully!");
	}

	
	/**
	 * Test for the getAddressesByCustomerId method of the AddressController.
	 * Verifies that the controller returns addresses associated with a customer ID.
	 */
	@Test
	void testGetAddressesByCustomerId() {
		// Mock the addressService to return the address list associated with customer
		// ID 1
		when(addressService.getAddressesByCustomerId(1L)).thenReturn(addressList);

		// Call the method under test
		ResponseEntity<List<Address>> responseEntity = addressController.getAddressesByCustomerId(1L);

		// Assert that the response status code is OK
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		// Assert that the response body matches the expected address list
		assertThat(responseEntity.getBody()).isEqualTo(addressList);
	}
}