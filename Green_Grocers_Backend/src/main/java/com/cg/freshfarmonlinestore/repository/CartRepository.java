package com.cg.freshfarmonlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.freshfarmonlinestore.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Find a Cart entity by the customer's ID
    Cart findByCustomer_CustomerId(long customerId);
}
