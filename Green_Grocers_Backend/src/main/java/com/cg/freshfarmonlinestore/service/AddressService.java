package com.cg.freshfarmonlinestore.service;

import java.util.List;
import com.cg.freshfarmonlinestore.entity.Address;

public interface AddressService {
    
    // Retrieves all addresses
    List<Address> getAllAddresses();
    
    // Retrieves an address by its ID
    Address getAddressById(long addressId);
    
    // Adds a new address for a specific customer
    Address addAddress(Address address, long customerId);
    
    // Updates an existing address
    Address updateAddress(long addressId, Address address);
    
    // Deletes an address by its ID
    String deleteAddress(long addressId);
    
    // Retrieves all addresses associated with a specific customer
    List<Address> getAddressesByCustomerId(long customerId);
}
