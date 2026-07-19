package com.cg.freshfarmonlinestore.service;

import java.util.List;

import com.cg.freshfarmonlinestore.dto.PaymentDto;

/**
 * PaymentService interface that defines methods for managing payments.
 * Provides methods for creating a payment, retrieving payments by ID,
 * retrieving all payments, and retrieving successful and unsuccessful payments.
 */
public interface PaymentService {

    // Creates a new payment for a given customer
    PaymentDto createPayment(Long customerId, PaymentDto paymentDTO);

    // Retrieves a payment by its ID
    PaymentDto getPaymentById(Long id);

    // Retrieves all payments
    List<PaymentDto> getAllPayments();

    // Retrieves all successful payments
    List<PaymentDto> getSuccessfulPayments();

    // Retrieves all unsuccessful payments
    List<PaymentDto> getUnsuccessfulPayments();

    // Retrieves all payments for a specific customer
    List<PaymentDto> getPaymentsByCustomerId(Long customerId); // New method
}
