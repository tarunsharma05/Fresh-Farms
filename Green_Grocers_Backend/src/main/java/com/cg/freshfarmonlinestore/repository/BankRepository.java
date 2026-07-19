package com.cg.freshfarmonlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.freshfarmonlinestore.entity.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {

    // Find a Bank entity by card number
    public Bank findByCardNumber(String cardNumber);

    // Find a Bank entity by UPI ID
    public Bank findByUpiId(String upiId);
}
