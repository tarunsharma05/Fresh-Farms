package com.cg.freshfarmonlinestore.service;
 
import com.cg.freshfarmonlinestore.entity.Bank;
 
import java.util.List;
import java.util.Optional;
 
public interface BankService {
    List<Bank> getAllBanks();
    Optional<Bank> getBankById(long bankId);
    Bank createBank(Bank bank);
    Bank updateBank(long bankId, Bank bankDetails);
    void deleteBank(long bankId);
}