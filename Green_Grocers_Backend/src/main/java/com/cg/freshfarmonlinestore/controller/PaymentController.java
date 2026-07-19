package com.cg.freshfarmonlinestore.controller;
 
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
import com.cg.freshfarmonlinestore.dto.PaymentDto;
import com.cg.freshfarmonlinestore.service.PaymentService;
 
import jakarta.validation.Valid;
 
/**
* PaymentController handles all HTTP requests related to payment operations.
* It provides endpoints for creating, retrieving, and listing payments.
*/
@RestController
@RequestMapping("/api/payments")
@CrossOrigin("*")
public class PaymentController {
 
    @Autowired
    private PaymentService paymentService;
 
    /**
     * Creates a new payment for a customer.
     * 
     * @param customerId the ID of the customer
     * @param paymentDto the payment details
     * @return ResponseEntity containing the created payment and HTTP status
     */
    @PostMapping("{customerId}")
    public ResponseEntity<PaymentDto> createPayment(@PathVariable("customerId") long customerId, @RequestBody PaymentDto paymentDto) {
        PaymentDto savedPayment = paymentService.createPayment(customerId, paymentDto);
        return new ResponseEntity<>(savedPayment, HttpStatus.CREATED);
    }
 
    /**
     * Retrieves a payment by its ID.
     * 
     * @param id the ID of the payment
     * @return ResponseEntity containing the payment details and HTTP status
     */
    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable("paymentId") Long paymentId) {
        PaymentDto paymentDTO = paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(paymentDTO, HttpStatus.OK);
    }
 
    /**
     * Retrieves all payments.
     * 
     * @return ResponseEntity containing the list of all payments and HTTP status
     */
    @GetMapping
    public ResponseEntity<List<PaymentDto>> getAllPayments() {
        List<PaymentDto> paymentDTOs = paymentService.getAllPayments();
        return new ResponseEntity<>(paymentDTOs, HttpStatus.OK);
    }
 
    /**
     * Retrieves all successful payments.
     * 
     * @return ResponseEntity containing the list of successful payments and HTTP status
     */
    @GetMapping("/successful")
    public ResponseEntity<List<PaymentDto>> getSuccessfulPayments() {
        List<PaymentDto> successfulPayments = paymentService.getSuccessfulPayments();
        return ResponseEntity.ok(successfulPayments);
    }
 
    /**
     * Retrieves all unsuccessful payments.
     * 
     * @return ResponseEntity containing the list of unsuccessful payments and HTTP status
     */
    @GetMapping("/unsuccessful")
    public ResponseEntity<List<PaymentDto>> getUnsuccessfulPayments() {
        List<PaymentDto> unsuccessfulPayments = paymentService.getUnsuccessfulPayments();
        return ResponseEntity.ok(unsuccessfulPayments);
    }
    /**
     * Retrieves all payments for a specific customer.
     * 
     * @param customerId the ID of the customer
     * @return ResponseEntity containing the list of payments for the customer and HTTP status
     */
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PaymentDto>> getPaymentsByCustomerId(@PathVariable("customerId") Long customerId) {
        List<PaymentDto> payments = paymentService.getPaymentsByCustomerId(customerId);
        return ResponseEntity.ok(payments);
    }
}