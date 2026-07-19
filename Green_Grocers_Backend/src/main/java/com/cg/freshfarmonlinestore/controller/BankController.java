package com.cg.freshfarmonlinestore.controller;
 
import com.cg.freshfarmonlinestore.entity.Bank;
import com.cg.freshfarmonlinestore.service.BankService;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
 
import java.util.List;
import java.util.Optional;
 
@RestController
@RequestMapping("/api/banks")
public class BankController {
 
    @Autowired
    private BankService bankService;
 
    @GetMapping
    public ResponseEntity<List<Bank>> getAllBanks() {
        List<Bank> banks = bankService.getAllBanks();
        return ResponseEntity.ok(banks);
    }
 
    @GetMapping("/{id}")
    public ResponseEntity<Bank> getBankById(@PathVariable("id") long id) {
        Optional<Bank> bank = bankService.getBankById(id);
        if (bank.isPresent()) {
            return ResponseEntity.ok(bank.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
 
    @PostMapping
    public ResponseEntity<Bank> createBank(@RequestBody Bank bank) {
        Bank savedBank = bankService.createBank(bank);
        return ResponseEntity.ok(savedBank);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<Bank> updateBank(@PathVariable("id") long id, @RequestBody Bank bankDetails) {
        try {
            Bank updatedBank = bankService.updateBank(id, bankDetails);
            return ResponseEntity.ok(updatedBank);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable("id") long id) {
        try {
            bankService.deleteBank(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
