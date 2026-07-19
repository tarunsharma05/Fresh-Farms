package com.cg.freshfarmonlinestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.service.AddressService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/api/addresses")
public class AddressController { 
 
    private AddressService addressService;

    /**
     * Get all addresses.
     * This method handles GET requests to "/api/addresses".
     * It retrieves a list of all addresses and returns them in the response body
     * with an HTTP status of OK.
     * 
     * @return ResponseEntity containing the list of addresses and HTTP status OK
     */
    @GetMapping
    public ResponseEntity<List<Address>> getAllAddresses() {
        List<Address> addresses = addressService.getAllAddresses();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    /**
     * Get address by ID.
     * This method handles GET requests to "/api/addresses/{addressId}".
     * It retrieves an address by its ID and returns it in the response body
     * with an HTTP status of OK.
     * 
     * @param addressId The ID of the address to retrieve
     * @return ResponseEntity containing the address and HTTP status OK
     */
    @GetMapping("/{addressId}")
    public ResponseEntity<Address> getAddressById(@PathVariable("addressId") long addressId) {
        Address address = addressService.getAddressById(addressId);
        return new ResponseEntity<>(address, HttpStatus.OK);
    }

    /**
     * Add a new address for a customer.
     * This method handles POST requests to "/api/addresses/{customerId}".
     * It creates a new address for the specified customer and returns the saved address
     * in the response body with an HTTP status of CREATED.
     * 
     * @param address The address to add
     * @param customerId The ID of the customer to whom the address belongs
     * @return ResponseEntity containing the saved address and HTTP status CREATED
     */
    @PostMapping("/{customerId}")
    public ResponseEntity<Address> addAddress(@Valid @RequestBody Address address, @PathVariable("customerId") long customerId) {
        Address savedAddress = addressService.addAddress(address, customerId);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    /**
     * Update an existing address.
     * This method handles PUT requests to "/api/addresses/{addressId}".
     * It updates the address with the given ID using the provided address details,
     * and returns the updated address in the response body with an HTTP status of OK.
     * 
     * @param addressId The ID of the address to update
     * @param address The updated address details
     * @return ResponseEntity containing the updated address and HTTP status OK
     */
    @PutMapping("/{addressId}")
    public ResponseEntity<Address> updateAddress(@PathVariable("addressId") long addressId, @Valid @RequestBody Address address) {
        Address updatedAddress = addressService.updateAddress(addressId, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    /**
     * Delete an address by its ID.
     * This method handles DELETE requests to "/api/addresses/{addressId}".
     * It deletes the address with the given ID and returns a response message
     * with an HTTP status of OK.
     * 
     * @param addressId The ID of the address to delete
     * @return ResponseEntity containing the response message and HTTP status OK
     */
    @DeleteMapping("/{addressId}")
    public ResponseEntity<String> deleteAddress(@PathVariable("addressId") long addressId) {
        String response = addressService.deleteAddress(addressId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Get addresses by customer ID.
     * This method handles GET requests to "/api/addresses/customer/{customerId}".
     * It retrieves a list of addresses associated with the specified customer ID
     * and returns them in the response body with an HTTP status of OK.
     * 
     * @param customerId The ID of the customer whose addresses to retrieve
     * @return ResponseEntity containing the list of addresses and HTTP status OK
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Address>> getAddressesByCustomerId(@PathVariable("customerId") long customerId) {
        List<Address> addresses = addressService.getAddressesByCustomerId(customerId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }
}
