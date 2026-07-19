package com.cg.freshfarmonlinestore.service.impl;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cg.freshfarmonlinestore.dto.PaymentDto;
import com.cg.freshfarmonlinestore.entity.Bank;
import com.cg.freshfarmonlinestore.entity.Cart;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Payment;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.BankRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.PaymentRepository;
 
class PaymentServiceImplTest {
 
    @Mock
    private PaymentRepository paymentRepository;
 
    @Mock
    private CustomerRepository customerRepository;
 
    @Mock
    private BankRepository bankRepository;
 
    @InjectMocks
    private PaymentServiceImpl paymentService;
 
    private Payment payment;
    private Customer customer;
    private PaymentDto paymentDto;
 
    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);
 
        // Create customer, payment, and paymentDto instances for testing
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setCart(new Cart());
        customer.getCart().setItemCount(3);
        customer.getCart().setTotalAmount(500.0);
 
        payment = new Payment();
        payment.setPaymentId(1L);
        payment.setCustomer(customer);
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(LocalTime.now());
        payment.setItemCount(3);
        payment.setTotalAmount(500.0);
        payment.setPaymentStatus("Payment Successful");
 
        paymentDto = new PaymentDto();
        paymentDto.setPaymentId(1L);
        paymentDto.setPaymentMode("card");
        paymentDto.setCardNumber("1234567890123456");
        paymentDto.setTotalAmount(500.0);
    }
 
    /**
     * Test for creating a new payment.
     * Verifies that the payment is successfully created and saved.
     */
    @Test
    void testCreatePayment() {

 
        // Mock customer and bank repository calls
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        when(bankRepository.findByCardNumber("1234567890123456")).thenReturn(new Bank());
 
        // Set the balance in the bank mock to cover the payment amount
        Bank mockBank = new Bank();
        mockBank.setBalance(600.0); // Ensure the bank has sufficient balance
        when(bankRepository.findByCardNumber("1234567890123456")).thenReturn(mockBank);
 
        // Call the method under test
        PaymentDto result = paymentService.createPayment(1L, paymentDto);
 
        // Assert that the result matches the expected values
        assertThat(result.getPaymentStatus()).isEqualTo("Payment Successful");
        assertThat(result.getItemCount()).isEqualTo(3);
        assertThat(result.getTotalAmount()).isEqualTo(500.0);
 
        // Verify that the save method was called once
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
 
 
    /**
     * Test for creating a payment with a non-existent customer.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testCreatePayment_CustomerNotFound() {
        // Mock the customer repository to return an empty Optional
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
 
        // Assert that the createPayment method throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> paymentService.createPayment(1L, paymentDto));
 
        // Verify that the save method was not called
        verify(paymentRepository, times(0)).save(any(Payment.class));
    }
 
    /**
     * Test for retrieving a payment by ID.
     * Verifies that the correct payment is returned.
     */
    @Test
    void testGetPaymentById() {
        // Mock the payment repository to return the payment
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(payment));
 
        // Call the method under test
        PaymentDto result = paymentService.getPaymentById(1L);
 
        // Assert that the result matches the expected values
        assertThat(result.getPaymentId()).isEqualTo(1L);
        assertThat(result.getPaymentStatus()).isEqualTo("Payment Successful");
 
        // Verify that the findById method was called once
        verify(paymentRepository, times(1)).findById(1L);
    }
 
    /**
     * Test for retrieving a payment by a non-existent ID.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetPaymentById_NotFound() {
        // Mock the payment repository to return an empty Optional
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Assert that the getPaymentById method throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentById(1L));
 
        // Verify that the findById method was called once
        verify(paymentRepository, times(1)).findById(1L);
    }
 
    /**
     * Test for retrieving all payments.
     * Verifies that the correct list of payments is returned.
     */
    @Test
    void testGetAllPayments() {
        // Mock the payment repository to return a list of payments
        when(paymentRepository.findAll()).thenReturn(Arrays.asList(payment));
 
        // Call the method under test
        List<PaymentDto> result = paymentService.getAllPayments();
 
        // Assert that the result matches the expected values
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPaymentId()).isEqualTo(1L);
 
        // Verify that the findAll method was called once
        verify(paymentRepository, times(1)).findAll();
    }
 
    /**
     * Test for retrieving all successful payments.
     * Verifies that the correct list of successful payments is returned.
     */
    @Test
    void testGetSuccessfulPayments() {
        // Mock the payment repository to return a list of successful payments
        when(paymentRepository.findByPaymentStatus("Payment Successful")).thenReturn(Arrays.asList(payment));
 
        // Call the method under test
        List<PaymentDto> result = paymentService.getSuccessfulPayments();
 
        // Assert that the result matches the expected values
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPaymentId()).isEqualTo(1L);
 
        // Verify that the findByPaymentStatus method was called once
        verify(paymentRepository, times(1)).findByPaymentStatus("Payment Successful");
    }
 
    /**
     * Test for retrieving all unsuccessful payments.
     * Verifies that the correct list of unsuccessful payments is returned.
     */
    @Test
    void testGetUnsuccessfulPayments() {
        // Mock the payment repository to return a list of unsuccessful payments
        when(paymentRepository.findByPaymentStatus("Payment Failed")).thenReturn(Arrays.asList(payment));
 
        // Call the method under test
        List<PaymentDto> result = paymentService.getUnsuccessfulPayments();
 
        // Assert that the result matches the expected values
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPaymentId()).isEqualTo(1L);
 
        // Verify that the findByPaymentStatus method was called once
        verify(paymentRepository, times(1)).findByPaymentStatus("Payment Failed");
    }
 
    /**
     * Test for retrieving payments by customer ID.
     * Verifies that the correct list of payments for the given customer is returned.
     */
    @Test
    void testGetPaymentsByCustomerId() {
        // Mock the customer repository to return the customer
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Mock the payment repository to return a list of payments for the customer
        when(paymentRepository.findByCustomer(customer)).thenReturn(Arrays.asList(payment));
 
        // Call the method under test
        List<PaymentDto> result = paymentService.getPaymentsByCustomerId(1L);
 
        // Assert that the result matches the expected values
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPaymentId()).isEqualTo(1L);
 
        // Verify that the findByCustomer method was called once
        verify(paymentRepository, times(1)).findByCustomer(customer);
    }
 
    /**
     * Test for retrieving payments by a non-existent customer ID.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void testGetPaymentsByCustomerId_CustomerNotFound() {
        // Mock the customer repository to return an empty Optional
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
 
        // Assert that the getPaymentsByCustomerId method throws a ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> paymentService.getPaymentsByCustomerId(1L));
 
        // Verify that the findByCustomer method was not called
        verify(paymentRepository, times(0)).findByCustomer(any(Customer.class));
    }

    @Test
    void testCreatePayment_UPISuccess() {
        // Mock customer and bank repository calls
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        // Set the UPI details in paymentDto
        paymentDto.setPaymentMode("upi");
        paymentDto.setUpiId("test@upi");
        // Set the balance in the bank mock to cover the payment amount
        Bank mockBank = new Bank();
        mockBank.setBalance(600.0); // Ensure the bank has sufficient balance
        when(bankRepository.findByUpiId("test@upi")).thenReturn(mockBank);
        // Call the method under test
        PaymentDto result = paymentService.createPayment(1L, paymentDto);
        // Assert that the result matches the expected values
        assertThat(result.getPaymentStatus()).isEqualTo("Payment Successful");
        assertThat(result.getItemCount()).isEqualTo(3);
        assertThat(result.getTotalAmount()).isEqualTo(500.0);
        // Verify that the save method was called once
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
    /**
     * Test for creating a payment with UPI mode and insufficient balance.
     * Verifies that the payment fails due to insufficient funds.
     */
    /**
     * Test for creating a payment with COD mode.
     * Verifies that the payment is successfully created and saved.
     */
    @Test
    void testCreatePayment_CODSuccess() {
        // Mock customer repository call
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        // Set the COD details in paymentDto
        paymentDto.setPaymentMode("cod");
        // Call the method under test
        PaymentDto result = paymentService.createPayment(1L, paymentDto);
        // Assert that the result matches the expected values
        assertThat(result.getPaymentStatus()).isEqualTo("Payment Successful");
        assertThat(result.getItemCount()).isEqualTo(3);
        assertThat(result.getTotalAmount()).isEqualTo(500.0);
        // Verify that the save method was called once
        verify(paymentRepository, times(1)).save(any(Payment.class));
    }
    /**
     * Test case for creating a payment with a successful card payment.
     * 
     * Steps:
     * 1. Set up the customer and bank entities with sufficient balance.
     * 2. Mock the repository methods to return the set up entities.
     * 3. Call the createPayment method.
     * 4. Verify the payment status and other details.
     */
    @Test
    public void testCreatePayment_SuccessfulCardPayment() {
        Long customerId = 1L;
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentMode("card");
        paymentDto.setCardNumber("1234567890");
        paymentDto.setTotalAmount(100.0);
 
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItemCount(2);
        cart.setTotalAmount(100.0);
        customer.setCart(cart);
 
        Bank bank = new Bank();
        bank.setBalance(200.0);
 
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
        when(bankRepository.findByCardNumber(paymentDto.getCardNumber())).thenReturn(bank);
 
        PaymentDto createdPayment = paymentService.createPayment(customerId, paymentDto);
 
        assertNotNull(createdPayment);
        assertEquals("Payment Successful", createdPayment.getPaymentStatus());
        assertEquals(LocalDate.now(), createdPayment.getPaymentDate());
        assertEquals(LocalTime.now().getHour(), createdPayment.getPaymentTime().getHour());
        assertEquals(LocalTime.now().getMinute(), createdPayment.getPaymentTime().getMinute());
        assertEquals(2, createdPayment.getItemCount());
        assertEquals(100.0, createdPayment.getTotalAmount());
    }
 
    /**
     * Test case for creating a payment with a failed card payment due to insufficient balance.
     * 
     * Steps:
     * 1. Set up the customer and bank entities with insufficient balance.
     * 2. Mock the repository methods to return the set up entities.
     * 3. Call the createPayment method.
     * 4. Verify the payment status is "Payment Failed".
     */
    @Test
    public void testCreatePayment_FailedCardPayment() {
        Long customerId = 1L;
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentMode("card");
        paymentDto.setCardNumber("1234567890");
        paymentDto.setTotalAmount(100.0);
 
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItemCount(2);
        cart.setTotalAmount(100.0);
        customer.setCart(cart);
 
        Bank bank = new Bank();
        bank.setBalance(50.0);
 
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
        when(bankRepository.findByCardNumber(paymentDto.getCardNumber())).thenReturn(bank);
 
        PaymentDto createdPayment = paymentService.createPayment(customerId, paymentDto);
 
        assertNotNull(createdPayment);
        assertEquals("Payment Failed", createdPayment.getPaymentStatus());
    }
 
    /**
     * Test case for creating a payment with a successful UPI payment.
     * 
     * Steps:
     * 1. Set up the customer and bank entities with sufficient balance.
     * 2. Mock the repository methods to return the set up entities.
     * 3. Call the createPayment method.
     * 4. Verify the payment status and other details.
     */
    @Test
    public void testCreatePayment_SuccessfulUpiPayment() {
        Long customerId = 1L;
        PaymentDto paymentDto = new PaymentDto();
        paymentDto.setPaymentMode("upi");
        paymentDto.setUpiId("test@upi");
        paymentDto.setTotalAmount(100.0);
 
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItemCount(2);
        cart.setTotalAmount(100.0);
        customer.setCart(cart);
 
        Bank bank = new Bank();
        bank.setBalance(200.0);
 
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
        when(bankRepository.findByUpiId(paymentDto.getUpiId())).thenReturn(bank);
 
        PaymentDto createdPayment = paymentService.createPayment(customerId, paymentDto);
 
        assertNotNull(createdPayment);
        assertEquals("Payment Successful", createdPayment.getPaymentStatus());
    }
 
 
    
}