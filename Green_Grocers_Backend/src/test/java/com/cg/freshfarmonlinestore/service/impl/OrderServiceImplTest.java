package com.cg.freshfarmonlinestore.service.impl;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cg.freshfarmonlinestore.dto.OrderDto;
import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.entity.Cart;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Order;
import com.cg.freshfarmonlinestore.entity.Payment;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.AddressRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.OrderRepository;
import com.cg.freshfarmonlinestore.repository.PaymentRepository;
import com.cg.freshfarmonlinestore.service.CartService;
 
class OrderServiceImplTest {
	@Mock
    private OrderRepository orderRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CartService cartService;
    @Mock
    private AddressRepository addressRepository;
    @InjectMocks
    private OrderServiceImpl orderService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

 // Test method to create order when customer is not found
    @Test
    void createOrder_CustomerNotFound_ThrowsException() {
        // Mocking scenario where customer is not found and asserting exception
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
        OrderDto orderDto = new OrderDto();
        assertThrows(RuntimeException.class, () -> orderService.createOrder(1L, 1L, 1L));
        verify(customerRepository, times(1)).findByCustomerId(1L);
    }

    // Test method to get order by ID when found
    @Test
    void getOrder_Found_ReturnsOrderDto() {
        // Mocking scenario where order is found by ID and asserting returned OrderDto
        Order order = new Order();
        order.setOrderId(1L);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        OrderDto result = orderService.getOrder(1L);
        assertNotNull(result);
        assertEquals(order.getOrderId(), result.getOrderId());
        verify(orderRepository, times(1)).findById(1L);
    }

    // Test method to get order by ID when not found
    @Test
    void getOrder_NotFound_ReturnsNull() {
        // Mocking scenario where order is not found by ID and asserting returned value as null
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        OrderDto result = orderService.getOrder(1L);
        assertNull(result);
        verify(orderRepository, times(1)).findById(1L);
    }

    // Test method to get all orders
    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Mocking scenario to return list of orders and asserting its size
        Order order1 = new Order();
        order1.setOrderId(1L);
        Order order2 = new Order();
        order2.setOrderId(2L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
        List<OrderDto> result = orderService.getAllOrders();
        assertEquals(2, result.size());
        verify(orderRepository, times(1)).findAll();
    }

    // Test method to delete an existing order
    @Test
    void deleteOrder_ExistingOrder_DeletesOrder() {
        // Mocking scenario to delete an existing order and asserting deletion
        when(orderRepository.existsById(1L)).thenReturn(true);
        boolean result = orderService.deleteOrder(1L);
        assertTrue(result);
        verify(orderRepository, times(1)).existsById(1L);
        verify(orderRepository, times(1)).deleteById(1L);
    }

    // Test method to delete a non-existing order
    @Test
    void deleteOrder_NonExistingOrder_ReturnsFalse() {
        // Mocking scenario where order does not exist and asserting false return
        when(orderRepository.existsById(1L)).thenReturn(false);
        boolean result = orderService.deleteOrder(1L);
        assertFalse(result);
        verify(orderRepository, times(1)).existsById(1L);
    }

    // Test method to get all orders sorted by order ID
    @Test
    void getAllOrdersByOrderId_ReturnsListOfOrdersSortedByOrderId() {
        // Mocking scenario to return list of orders sorted by order ID and asserting order
        Order order1 = new Order();
        order1.setOrderId(2L);
        Order order2 = new Order();
        order2.setOrderId(1L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderDto> result = orderService.getAllOrdersByOrderId();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(2L, result.get(1).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    // Test method to get total count of distinct orders
    @Test
    void getTotalDistinctOrderCount_ReturnsCountOfDistinctOrders() {
        // Mocking scenario to return total count of distinct orders and asserting count
        Order order1 = new Order();
        order1.setOrderId(1L);
        Order order2 = new Order();
        order2.setOrderId(1L); // Same orderId as order1
        Order order3 = new Order();
        order3.setOrderId(2L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2, order3));

        Long result = orderService.getTotalDistinctOrderCount();

        assertEquals(2L, result);
        verify(orderRepository, times(1)).findAll();
    }

    // Test method to update order status
    @Test
    void updateOrderStatus_ExistingOrder_UpdatesOrderStatus() {
        // Mocking scenario to update order status and asserting the updated status
        Long orderId = 1L;
        String newStatus = "Shipped";
        Order order = new Order();
        order.setOrderId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDto result = orderService.updateOrderStatus(orderId, newStatus);

        assertNotNull(result);
        assertEquals(newStatus, result.getOrderStatus());
        verify(orderRepository, times(1)).findById(orderId);
        verify(orderRepository, times(1)).save(order);
    }
 
    @Test
    void getAllOrdersSortedByDate_ReturnsListOfOrdersSortedByDate() {
        Order order1 = new Order();
        order1.setOrderDate(LocalDate.now().minusDays(1));
        Order order2 = new Order();
        order2.setOrderDate(LocalDate.now());
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));
 
        List<OrderDto> result = orderService.getAllOrdersSortedByDate();
 
        assertEquals(2, result.size());
        assertTrue(result.get(0).getOrderDate().isAfter(result.get(1).getOrderDate()));
        verify(orderRepository, times(1)).findAll();
    }
 
    @Test
    void getOrdersByCustomerId_ExistingCustomer_ReturnsListOfOrders() {
        Long customerId = 1L;
        Customer customer = new Customer();
        List<Order> orders = Arrays.asList(new Order(), new Order());
        customer.setOrders(orders);
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));
 
        List<OrderDto> result = orderService.getOrdersByCustomerId(customerId);
 
        assertNotNull(result);
        assertEquals(orders.size(), result.size());
        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }
 
    @Test
    void getOrdersByCustomerId_NonExistingCustomer_ThrowsException() {
        Long customerId = 1L;
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.empty());
 
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrdersByCustomerId(customerId));
 
        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }
 
    /**
     * Test case for creating an order when the payment is not found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void createOrder_PaymentNotFound_ThrowsException() {
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(new Customer()));
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(1L, 1L, 1L));
        verify(paymentRepository, times(1)).findById(1L);
    }

    /**
     * Test case for creating an order when the address is not found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void createOrder_AddressNotFound_ThrowsException() {
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(new Customer()));
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(new Payment()));
        when(addressRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(1L, 1L, 1L));
        verify(addressRepository, times(1)).findById(1L);
    }


    /**
     * Test case for retrieving all orders with no orders present.
     * Verifies that an empty list is returned.
     */
    @Test
    void getAllOrders_NoOrders_ReturnsEmptyList() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());

        List<OrderDto> result = orderService.getAllOrders();

        assertTrue(result.isEmpty());
        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Test case for retrieving an order by ID and verifying its fields.
     */
    @Test
    void getOrderById_VerifyFields() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setOrderDate(LocalDate.now());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        OrderDto result = orderService.getOrder(1L);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals(order.getOrderDate(), result.getOrderDate());
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Test case for retrieving all orders sorted by order ID and verifying the sort order.
     */
    @Test
    void getAllOrdersSortedByOrderId_VerifySortOrder() {
        Order order1 = new Order();
        order1.setOrderId(2L);
        Order order2 = new Order();
        order2.setOrderId(1L);
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderDto> result = orderService.getAllOrdersByOrderId();

        assertEquals(1L, result.get(0).getOrderId());
        assertEquals(2L, result.get(1).getOrderId());
        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Test case for updating the order status and verifying the updated status.
     */
    @Test
    void updateOrderStatus_VerifyUpdatedStatus() {
        Long orderId = 1L;
        String newStatus = "Delivered";
        Order order = new Order();
        order.setOrderId(orderId);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderDto result = orderService.updateOrderStatus(orderId, newStatus);

        assertEquals(newStatus, result.getOrderStatus());
        verify(orderRepository, times(1)).save(order);
    }

    /**
     * Test case for retrieving all orders sorted by date and verifying the sort order.
     */
    @Test
    void getAllOrdersSortedByDate_VerifySortOrder() {
        Order order1 = new Order();
        order1.setOrderDate(LocalDate.now().minusDays(2));
        Order order2 = new Order();
        order2.setOrderDate(LocalDate.now().minusDays(1));
        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<OrderDto> result = orderService.getAllOrdersSortedByDate();

        assertTrue(result.get(0).getOrderDate().isAfter(result.get(1).getOrderDate()));
        verify(orderRepository, times(1)).findAll();
    }

    /**
     * Test case for retrieving orders by customer ID when customer exists.
     * Verifies that the correct orders are returned.
     */
    @Test
    void getOrdersByCustomerId_ExistingCustomer_ReturnsOrders() {
        Long customerId = 1L;
        Customer customer = new Customer();
        Order order1 = new Order();
        Order order2 = new Order();
        customer.setOrders(Arrays.asList(order1, order2));
        when(customerRepository.findByCustomerId(customerId)).thenReturn(Optional.of(customer));

        List<OrderDto> result = orderService.getOrdersByCustomerId(customerId);

        assertEquals(2, result.size());
        verify(customerRepository, times(1)).findByCustomerId(customerId);
    }

    /**
     * Test case for updating order status when order is not found.
     * Verifies that a ResourceNotFoundException is thrown.
     */
    @Test
    void updateOrderStatus_OrderNotFound_ThrowsException() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.updateOrderStatus(1L, "Shipped"));
        verify(orderRepository, times(1)).findById(1L);
    }

    /**
     * Test case for getting total distinct order count when there are no orders.
     * Verifies that the count is zero.
     */
    @Test
    void getTotalDistinctOrderCount_NoOrders_ReturnsZero() {
        when(orderRepository.findAll()).thenReturn(Arrays.asList());

        Long result = orderService.getTotalDistinctOrderCount();

        assertEquals(0L, result);
        verify(orderRepository, times(1)).findAll();
    }
    
 
}