package com.cg.freshfarmonlinestore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cg.freshfarmonlinestore.dto.OrderDto;
import com.cg.freshfarmonlinestore.service.OrderService;

public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateOrderStatus_ValidInput_ReturnsUpdatedOrder() {
        // Mock the orderService.updateOrderStatus method to return an updated OrderDto
        OrderDto updatedOrder = new OrderDto();
        when(orderService.updateOrderStatus(anyLong(), anyString())).thenReturn(updatedOrder);

        // Call the controller method
        ResponseEntity<OrderDto> response = orderController.updateOrderStatus(1L, "SHIPPED");

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body matches the updated OrderDto
        assertEquals(updatedOrder, response.getBody());
    }

    @Test
    void deleteOrders_ValidInput_ReturnsTrue() {
        // Mock the orderService.deleteOrder method to return true
        when(orderService.deleteOrder(1L)).thenReturn(true);

        // Call the controller method
        ResponseEntity<Boolean> response = orderController.deleteOrders(1L);

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is true
        assertTrue(response.getBody());
    }

    @Test
    void getOrder_ValidInput_ReturnsOrder() {
        // Mock the orderService.getOrder method to return an OrderDto
        OrderDto orderDto = new OrderDto();
        when(orderService.getOrder(1L)).thenReturn(orderDto);

        // Call the controller method
        ResponseEntity<OrderDto> response = orderController.getOrder(1L);

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body matches the OrderDto
        assertEquals(orderDto, response.getBody());
    }

    @Test
    void getAllOrders_ReturnsListOfOrders() {
        // Mock the orderService.getAllOrders method to return a list of OrderDto
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        when(orderService.getAllOrders()).thenReturn(Arrays.asList(orderDto1, orderDto2));

        // Call the controller method
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrders();

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body contains 2 OrderDto objects
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getOrdersByCustomerId_ValidInput_ReturnsListOfOrders() {
        // Mock the orderService.getOrdersByCustomerId method to return a list of OrderDto
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        when(orderService.getOrdersByCustomerId(anyLong())).thenReturn(Arrays.asList(orderDto1, orderDto2));

        // Call the controller method
        ResponseEntity<List<OrderDto>> response = orderController.getOrdersByCustomerId(1L);

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body contains 2 OrderDto objects
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAllOrdersByOrderId_ReturnsListOfOrders() {
        // Mock the orderService.getAllOrdersByOrderId method to return a list of OrderDto
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        when(orderService.getAllOrdersByOrderId()).thenReturn(Arrays.asList(orderDto1, orderDto2));

        // Call the controller method
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrdersByOrderId();

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body contains 2 OrderDto objects
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getAllOrdersSortedByDate_ReturnsListOfOrders() {
        // Mock the orderService.getAllOrdersSortedByDate method to return a list of OrderDto
        OrderDto orderDto1 = new OrderDto();
        OrderDto orderDto2 = new OrderDto();
        when(orderService.getAllOrdersSortedByDate()).thenReturn(Arrays.asList(orderDto1, orderDto2));

        // Call the controller method
        ResponseEntity<List<OrderDto>> response = orderController.getAllOrdersSortedByDate();

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body contains 2 OrderDto objects
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getTotalDistinctOrderCount_ReturnsCount() {
        // Mock the orderService.getTotalDistinctOrderCount method to return a count
        long count = 5L;
        when(orderService.getTotalDistinctOrderCount()).thenReturn(count);

        // Call the controller method
        ResponseEntity<Long> response = orderController.getTotalDistinctOrderCount();

        // Assert that the response status is 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Assert that the response body is not null
        assertNotNull(response.getBody());

        // Assert that the response body matches the count
        assertEquals(count, response.getBody().longValue());
    }
}
