
package com.cg.freshfarmonlinestore.service.impl;
 
import com.cg.freshfarmonlinestore.entity.Bank;

import com.cg.freshfarmonlinestore.repository.BankRepository;

import com.cg.freshfarmonlinestore.service.BankService;
 
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
 
import java.util.List;

import java.util.Optional;
 
@Service

public class BankServiceImpl implements BankService {
 
    @Autowired

    private BankRepository bankRepository;
 
    @Override

    public List<Bank> getAllBanks() {

        return bankRepository.findAll();

    }
 
    @Override

    public Optional<Bank> getBankById(long bankId) {

        return bankRepository.findById(bankId);

    }
 
    @Override

    public Bank createBank(Bank bank) {

        return bankRepository.save(bank);

    }
 
    @Override

    public Bank updateBank(long bankId, Bank bankDetails) {

        Optional<Bank> optionalBank = bankRepository.findById(bankId);

        if (optionalBank.isPresent()) {

            Bank existingBank = optionalBank.get();

            existingBank.setName(bankDetails.getName());

            existingBank.setBankName(bankDetails.getBankName());

            existingBank.setUpiId(bankDetails.getUpiId());

            existingBank.setCardNumber(bankDetails.getCardNumber());

            existingBank.setBalance(bankDetails.getBalance());

            return bankRepository.save(existingBank);

        } else {

            throw new RuntimeException("Bank not found with id " + bankId);

        }

    }
 
    @Override

    public void deleteBank(long bankId) {

        bankRepository.deleteById(bankId);

    }

}
