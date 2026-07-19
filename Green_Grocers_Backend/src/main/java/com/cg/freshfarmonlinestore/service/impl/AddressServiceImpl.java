package com.cg.freshfarmonlinestore.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.AddressRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.service.AddressService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor   
public class AddressServiceImpl implements AddressService {

    private  AddressRepository addressRepository;
    private  CustomerRepository customerRepository;

    /**
     * Retrieves all addresses.
     *
     * @return a list of all addresses
     */
    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    /**
     * Retrieves an address by its ID.
     *
     * @param addressId the ID of the address to retrieve
     * @return the address with the given ID
     * @throws ResourceNotFoundException if the address is not found
     */
    @Override
    public Address getAddressById(long addressId) {
        return addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
    }

    /**
     * Adds a new address for a specific customer.
     *
     * @param address the address to add
     * @param customerId the ID of the customer to associate with the address
     * @return the newly added address
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public Address addAddress(Address address, long customerId) {
        // Find the customer by ID
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        
        // Set the customer for the address
        address.setCustomer(customer);
        
        // Save the address
        Address newAddress = addressRepository.save(address);
        
        // Add the address to the customer's addresses list
        customer.getAddresses().add(newAddress);
        
        // Save the customer
        customerRepository.save(customer);
        
        return newAddress;
    }

    /**
     * Updates an existing address.
     *
     * @param addressId the ID of the address to update
     * @param address the new address details
     * @return the updated address
     * @throws ResourceNotFoundException if the address is not found
     */
    @Override
    public Address updateAddress(long addressId, Address address) {
        // Find the existing address by ID
        Address existingAddress = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        // Update address fields
        existingAddress.setHouseNo(address.getHouseNo());
        existingAddress.setLandmark(address.getLandmark());
        existingAddress.setCity(address.getCity());
        existingAddress.setState(address.getState());
        existingAddress.setCountry(address.getCountry());
        existingAddress.setPinCode(address.getPinCode());
        
        // Save and return the updated address
        return addressRepository.save(existingAddress);
    }

    /**
     * Deletes an address by its ID.
     *
     * @param addressId the ID of the address to delete
     * @return a message indicating the deletion status
     * @throws ResourceNotFoundException if the address is not found
     */
    @Override
    public String deleteAddress(long addressId) {
        // Find the address by ID
        Address address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + addressId));
        
        // Delete the address
        addressRepository.delete(address);
        
        // Return a deletion message
        return "Address deleted with ID: " + addressId;
    }

    /**
     * Retrieves addresses associated with a specific customer.
     *
     * @param customerId the ID of the customer whose addresses are to be retrieved
     * @return a list of addresses associated with the customer
     * @throws ResourceNotFoundException if the customer is not found
     */
    @Override
    public List<Address> getAddressesByCustomerId(long customerId) {
        // Find the customer by ID
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        
        // Return the addresses associated with the customer
        return customer.getAddresses();
    }
}
