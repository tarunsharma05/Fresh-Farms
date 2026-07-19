package com.cg.freshfarmonlinestore.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.freshfarmonlinestore.dto.PaymentDto;
import com.cg.freshfarmonlinestore.service.PaymentService;

/**
 * Unit tests for the PaymentController class.
 */
class PaymentControllerTest {

    @Mock
    private PaymentService paymentService;  // Mocked PaymentService

    @InjectMocks
    private PaymentController paymentController;  // Controller under test

    private PaymentDto paymentDto;  // Test PaymentDto object
    private List<PaymentDto> paymentList;  // List of PaymentDto objects for testing

    /**
     * Set up the test data and initialize mocks before each test.
     */
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Create a test PaymentDto object
        paymentDto = new PaymentDto();
        paymentDto.setPaymentId(1L);
        paymentDto.setPaymentStatus("Payment Successful");
        paymentDto.setItemCount(3);
        paymentDto.setTotalAmount(500.0);

        // Create a list containing the test PaymentDto
        paymentList = Arrays.asList(paymentDto);
    }

    /**
     * Test for createPayment method.
     * This test verifies that the createPayment method in the controller correctly handles creating a new payment.
     */
    @Test
    void testCreatePayment() {
        // Mock the paymentService.createPayment method to return the test PaymentDto
        when(paymentService.createPayment(anyLong(), any(PaymentDto.class))).thenReturn(paymentDto);

        // Call the controller method
        ResponseEntity<PaymentDto> responseEntity = paymentController.createPayment(1L, paymentDto);

        // Assert that the response status is 201 CREATED
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Assert that the response body matches the test PaymentDto
        assertThat(responseEntity.getBody()).isEqualTo(paymentDto);

        // Verify that paymentService.createPayment was called exactly once
        verify(paymentService, times(1)).createPayment(anyLong(), any(PaymentDto.class));
    }

    /**
     * Test for getPaymentById method.
     * This test verifies that the getPaymentById method in the controller correctly retrieves a payment by its ID.
     */
    @Test
    void testGetPaymentById() {
        // Mock the paymentService.getPaymentById method to return the test PaymentDto
        when(paymentService.getPaymentById(anyLong())).thenReturn(paymentDto);

        // Call the controller method
        ResponseEntity<PaymentDto> responseEntity = paymentController.getPaymentById(1L);

        // Assert that the response status is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert that the response body matches the test PaymentDto
        assertThat(responseEntity.getBody()).isEqualTo(paymentDto);

        // Verify that paymentService.getPaymentById was called exactly once
        verify(paymentService, times(1)).getPaymentById(anyLong());
    }

    /**
     * Test for getAllPayments method.
     * This test verifies that the getAllPayments method in the controller correctly retrieves all payments.
     */
    @Test
    void testGetAllPayments() {
        // Mock the paymentService.getAllPayments method to return the list of PaymentDto objects
        when(paymentService.getAllPayments()).thenReturn(paymentList);

        // Call the controller method
        ResponseEntity<List<PaymentDto>> responseEntity = paymentController.getAllPayments();

        // Assert that the response status is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert that the response body matches the list of PaymentDto objects
        assertThat(responseEntity.getBody()).isEqualTo(paymentList);

        // Verify that paymentService.getAllPayments was called exactly once
        verify(paymentService, times(1)).getAllPayments();
    }

    /**
     * Test for getSuccessfulPayments method.
     * This test verifies that the getSuccessfulPayments method in the controller correctly retrieves all successful payments.
     */
    @Test
    void testGetSuccessfulPayments() {
        // Mock the paymentService.getSuccessfulPayments method to return the list of PaymentDto objects
        when(paymentService.getSuccessfulPayments()).thenReturn(paymentList);

        // Call the controller method
        ResponseEntity<List<PaymentDto>> responseEntity = paymentController.getSuccessfulPayments();

        // Assert that the response status is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert that the response body matches the list of PaymentDto objects
        assertThat(responseEntity.getBody()).isEqualTo(paymentList);

        // Verify that paymentService.getSuccessfulPayments was called exactly once
        verify(paymentService, times(1)).getSuccessfulPayments();
    }

    /**
     * Test for getUnsuccessfulPayments method.
     * This test verifies that the getUnsuccessfulPayments method in the controller correctly retrieves all unsuccessful payments.
     */
    @Test
    void testGetUnsuccessfulPayments() {
        // Mock the paymentService.getUnsuccessfulPayments method to return the list of PaymentDto objects
        when(paymentService.getUnsuccessfulPayments()).thenReturn(paymentList);

        // Call the controller method
        ResponseEntity<List<PaymentDto>> responseEntity = paymentController.getUnsuccessfulPayments();

        // Assert that the response status is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert that the response body matches the list of PaymentDto objects
        assertThat(responseEntity.getBody()).isEqualTo(paymentList);

        // Verify that paymentService.getUnsuccessfulPayments was called exactly once
        verify(paymentService, times(1)).getUnsuccessfulPayments();
    }

    /**
     * Test for getPaymentsByCustomerId method.
     * This test verifies that the getPaymentsByCustomerId method in the controller correctly retrieves payments by customer ID.
     */
    @Test
    void testGetPaymentsByCustomerId() {
        // Mock the paymentService.getPaymentsByCustomerId method to return the list of PaymentDto objects
        when(paymentService.getPaymentsByCustomerId(anyLong())).thenReturn(paymentList);

        // Call the controller method
        ResponseEntity<List<PaymentDto>> responseEntity = paymentController.getPaymentsByCustomerId(1L);

        // Assert that the response status is 200 OK
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        // Assert that the response body matches the list of PaymentDto objects
        assertThat(responseEntity.getBody()).isEqualTo(paymentList);

        // Verify that paymentService.getPaymentsByCustomerId was called exactly once
        verify(paymentService, times(1)).getPaymentsByCustomerId(anyLong());
    }
}
