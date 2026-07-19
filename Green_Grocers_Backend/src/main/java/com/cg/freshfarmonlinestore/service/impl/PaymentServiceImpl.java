package com.cg.freshfarmonlinestore.service.impl;
 
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cg.freshfarmonlinestore.dto.PaymentDto;
import com.cg.freshfarmonlinestore.entity.Bank;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Payment;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.BankRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.PaymentRepository;
import com.cg.freshfarmonlinestore.service.PaymentService;
 
@Service
public class PaymentServiceImpl implements PaymentService {
 
    @Autowired
    private PaymentRepository paymentRepository;
 
    @Autowired
    private CustomerRepository customerRepository;
 
    @Autowired
    private BankRepository bankRepository;
 
    /**
     * Method to create a new payment for a given customer.
     *
     * @param customerId the ID of the customer
     * @param paymentDto the details of the payment to be created
     * @return the created PaymentDto object
     */
    @Override
    public PaymentDto createPayment(Long customerId, PaymentDto paymentDto) {
        // Retrieve the customer by ID, throw an exception if not found
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
 
        // Create a new Payment entity and copy properties from the DTO
        Payment payment = new Payment();
        BeanUtils.copyProperties(paymentDto, payment);
        payment.setCustomer(customer);
 
        // Validate the payment and set the payment status
        String paymentStatus = validatePayment(paymentDto, customer);
        payment.setPaymentStatus(paymentStatus);
 
        // Set the payment date, time, item count, and total amount
        payment.setPaymentDate(LocalDate.now());
        payment.setPaymentTime(LocalTime.now());
        payment.setItemCount(customer.getCart().getItemCount());
//        payment.setTotalAmount(customer.getCart().getTotalAmount());
        
        payment.setTotalAmount(paymentDto.getTotalAmount());
 
        // Save the payment entity
        paymentRepository.save(payment);
 
        // Create and return the response DTO
        PaymentDto responseDTO = new PaymentDto();
        BeanUtils.copyProperties(payment, responseDTO);
        return responseDTO;
    }
 
    /**
     * Method to validate the payment based on the payment mode and customer details.
     *
     * @param paymentDto the payment details
     * @param customer the customer details
     * @return the payment status as a string
     */
    private String validatePayment(PaymentDto paymentDto, Customer customer) {
        double totalAmount = customer.getCart().getTotalAmount();
 
        switch (paymentDto.getPaymentMode().toLowerCase()) {
            case "card":
                // Validate payment by card number
                Bank cardBank = bankRepository.findByCardNumber(paymentDto.getCardNumber());
                if (cardBank != null && cardBank.getBalance() >= totalAmount) {
                    return "Payment Successful";
                }
                return "Payment Failed";
 
            case "upi":
                // Validate payment by UPI ID
                Bank upiBank = bankRepository.findByUpiId(paymentDto.getUpiId());
                if (upiBank != null && upiBank.getBalance() >= totalAmount) {
                    return "Payment Successful";
                }
                return "Payment Failed";
 
               
            case "cod":
                // Cash on delivery is always successful
                return "Payment Successful";
 
            default:
                throw new RuntimeException("Invalid payment mode: " + paymentDto.getPaymentMode());
        }
    }
 
    /**
     * Method to get a payment by its ID.
     *
     * @param id the ID of the payment
     * @return the PaymentDto object corresponding to the given ID
     */
    @Override
    public PaymentDto getPaymentById(Long id) {
        // Retrieve the payment by ID, throw an exception if not found
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));
 
        // Create and return the response DTO
        PaymentDto paymentDTO = new PaymentDto();
        BeanUtils.copyProperties(payment, paymentDTO);
        return paymentDTO;
    }
 
    /**
     * Method to get all payments.
     *
     * @return a list of all PaymentDto objects
     */
    @Override
    public List<PaymentDto> getAllPayments() {
        // Retrieve all payments and convert them to DTOs
        return paymentRepository.findAll().stream().map(payment -> {
            PaymentDto paymentDTO = new PaymentDto();
            BeanUtils.copyProperties(payment, paymentDTO);
            return paymentDTO;
        }).collect(Collectors.toList());
    }
 
    /**
     * Method to get all successful payments.
     *
     * @return a list of successful PaymentDto objects
     */
    @Override
    public List<PaymentDto> getSuccessfulPayments() {
        // Retrieve successful payments and convert them to DTOs
        return paymentRepository.findByPaymentStatus("Payment Successful").stream().map(payment -> {
            PaymentDto paymentDTO = new PaymentDto();
            BeanUtils.copyProperties(payment, paymentDTO);
            return paymentDTO;
        }).collect(Collectors.toList());
    }
 
    /**
     * Method to get all unsuccessful payments.
     *
     * @return a list of unsuccessful PaymentDto objects
     */
    @Override
    public List<PaymentDto> getUnsuccessfulPayments() {
        // Retrieve unsuccessful payments and convert them to DTOs
        return paymentRepository.findByPaymentStatus("Payment Failed").stream().map(payment -> {
            PaymentDto paymentDTO = new PaymentDto();
            BeanUtils.copyProperties(payment, paymentDTO);
            return paymentDTO;
        }).collect(Collectors.toList());
    }
    /**
     * Method to get payments by customerId.
     *
     * @return a list of payments of particular customer
     */
    @Override
    public List<PaymentDto> getPaymentsByCustomerId(Long customerId) {
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + customerId));
 
        return paymentRepository.findByCustomer(customer).stream().map(payment -> {
            PaymentDto paymentDTO = new PaymentDto();
            BeanUtils.copyProperties(payment, paymentDTO);
            return paymentDTO;
        }).collect(Collectors.toList());
    }
}