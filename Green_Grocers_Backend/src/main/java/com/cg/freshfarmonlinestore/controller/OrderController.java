package com.cg.freshfarmonlinestore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cg.freshfarmonlinestore.dto.OrderDto;
import com.cg.freshfarmonlinestore.service.OrderService;

/**
 * REST controller for managing orders.
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * Endpoint to create a new order.
     * 
     * @param customerId The ID of the customer placing the order.
     * @param paymentId  The ID of the payment associated with the order.
     * @param addressId  The ID of the delivery address for the order.
     * @return ResponseEntity containing the created order DTO.
     */
    @PostMapping("/createorder/{customerId}/{paymentId}/{addressId}")
    public ResponseEntity<OrderDto> createOrder(@PathVariable("customerId") Long customerId, @PathVariable("paymentId") Long paymentId,
                                                @PathVariable("addressId") Long addressId) {
        OrderDto savedOrder = orderService.createOrder(customerId, paymentId, addressId);
        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

    /**
     * Endpoint to update the status of an order.
     * 
     * @param orderId The ID of the order to update.
     * @param status  The new status of the order.
     * @return ResponseEntity containing the updated order DTO.
     */
    @PutMapping("/{orderId}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable("orderId") Long orderId, @RequestParam String status) {
        OrderDto updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    /**
     * Endpoint to delete an order.
     * 
     * @param orderId The ID of the order to delete.
     * @return ResponseEntity containing a boolean indicating the success of the deletion.
     */
    @DeleteMapping("/admin/{orderId}")
    public ResponseEntity<Boolean> deleteOrders(@PathVariable("orderId") Long orderId) {
        Boolean res = orderService.deleteOrder(orderId);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve an order by ID.
     * 
     * @param orderId The ID of the order to retrieve.
     * @return ResponseEntity containing the order DTO.
     */
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("orderId") Long orderId) {
        OrderDto dto = orderService.getOrder(orderId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all orders.
     * 
     * @return ResponseEntity containing a list of order DTOs.
     */
    @GetMapping("/all")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> list = orderService.getAllOrders();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve orders by customer ID.
     * 
     * @param customerId The ID of the customer whose orders are to be retrieved.
     * @return ResponseEntity containing a list of order DTOs for the specified customer.
     */
    @GetMapping("customerId/{customerId}")
    public ResponseEntity<List<OrderDto>> getOrdersByCustomerId(@PathVariable("customerId") Long customerId) {
        List<OrderDto> list = orderService.getOrdersByCustomerId(customerId);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all orders sorted by order ID.
     * 
     * @return ResponseEntity containing a list of order DTOs sorted by order ID.
     */
    @GetMapping("/sortedOrders")
    public ResponseEntity<List<OrderDto>> getAllOrdersByOrderId() {
        List<OrderDto> list = orderService.getAllOrdersByOrderId();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /**
     * Endpoint to retrieve all orders sorted by date.
     * 
     * @return ResponseEntity containing a list of order DTOs sorted by date.
     */
    @GetMapping("/sortedByDate")
    public ResponseEntity<List<OrderDto>> getAllOrdersSortedByDate() {
        List<OrderDto> sortedOrders = orderService.getAllOrdersSortedByDate();
        return ResponseEntity.ok(sortedOrders);
    }

    /**
     * Endpoint to retrieve the total count of distinct orders.
     * 
     * @return ResponseEntity containing the total count of distinct orders.
     */
    @GetMapping("/admin/totalOrders")
    public ResponseEntity<Long> getTotalDistinctOrderCount() {
        long result = orderService.getTotalDistinctOrderCount();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
