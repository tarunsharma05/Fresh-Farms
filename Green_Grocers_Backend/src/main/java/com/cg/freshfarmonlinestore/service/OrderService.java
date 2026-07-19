package com.cg.freshfarmonlinestore.service;

import java.util.List;

import com.cg.freshfarmonlinestore.dto.OrderDto;

/**
 * Service interface for managing orders.
 */
public interface OrderService {

    // Creates a new order for a customer
    public OrderDto createOrder(Long customerId, Long paymentId, Long addressId);

    // Retrieves all orders in the system
    public List<OrderDto> getAllOrders();

    // Retrieves the details of a specific order
    public OrderDto getOrder(Long orderId);

    // Deletes an order from the system
    public boolean deleteOrder(Long orderId);

    // Updates the status of an order
    public OrderDto updateOrderStatus(Long orderId, String newStatus);

    // Retrieves all orders sorted by date
    public List<OrderDto> getAllOrdersSortedByDate();

    // Retrieves all orders sorted by order ID
    public List<OrderDto> getAllOrdersByOrderId();

    // Retrieves the total count of distinct orders in the system
    public Long getTotalDistinctOrderCount();

    // Retrieves orders by customer ID
    public List<OrderDto> getOrdersByCustomerId(Long customerId);
}
