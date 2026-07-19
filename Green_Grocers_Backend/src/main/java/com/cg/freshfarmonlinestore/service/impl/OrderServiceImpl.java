package com.cg.freshfarmonlinestore.service.impl;
 
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cg.freshfarmonlinestore.dto.OrderDto;
import com.cg.freshfarmonlinestore.entity.Address;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Order;
import com.cg.freshfarmonlinestore.entity.OrderedItem;
import com.cg.freshfarmonlinestore.entity.Payment;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.AddressRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.OrderRepository;
import com.cg.freshfarmonlinestore.repository.PaymentRepository;
import com.cg.freshfarmonlinestore.service.CartService;
import com.cg.freshfarmonlinestore.service.OrderService;
 
/**
* Implementation of the OrderService interface providing functionalities for
* managing orders in the online store.
*/
@Service
public class OrderServiceImpl implements OrderService {
 
	@Autowired
	private OrderRepository orderRepository;
 
	@Autowired
	private CustomerRepository customerRepository;
 
	@Autowired
	private PaymentRepository paymentRepository;
 
	@Autowired
	private CartService cartService;
 
	@Autowired
	private AddressRepository addressRepository;
 
	/**
	 * Creates a new order for a customer.
	 *
	 * @param customerId The ID of the customer placing the order.
	 * @param paymentId  The ID of the payment associated with the order.
	 * @param dto        The order details.
	 * @param addressId  The ID of the delivery address for the order.
	 * @return The DTO representing the created order.
	 */
	@Override
	public OrderDto createOrder(Long customerId, Long paymentId,  Long addressId) {
		// Fetch the customer
		Customer customer = customerRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + paymentId));
 
		// Fetch the payment
		Payment payment = paymentRepository.findById(paymentId)
				.orElseThrow(() -> new ResourceNotFoundException("Payment not found"));
 
		Address address = addressRepository.findById(addressId)
				.orElseThrow(() -> new ResourceNotFoundException("No Address with this id" + addressId));
 
		// Create the new order'
		OrderDto dto= new OrderDto();
		Order order = new Order();
		BeanUtils.copyProperties(dto, order);
		order.setCustomer(customer);
 
		List<OrderedItem> orderedItems = customer.getCart().getItems().stream().map(cartItem -> {
			OrderedItem orderedItem = new OrderedItem();
			BeanUtils.copyProperties(cartItem, orderedItem);
			orderedItem.setOrder(order);
			return orderedItem;
		}).collect(Collectors.toList());
        order.setOrderStatus("Order Placed");
		order.setOrderedItems(orderedItems);
		order.setPayment(payment);
		order.setOrderDate(LocalDate.now());
		
		Address orderAddress = new Address();
        orderAddress.setCity(address.getCity());
        orderAddress.setCountry(address.getCountry());
        orderAddress.setHouseNo(address.getHouseNo());
        orderAddress.setLandmark(address.getLandmark());
        orderAddress.setPinCode(address.getPinCode());
        orderAddress.setState(address.getState());
        addressRepository.save(orderAddress);
        order.setAddress(orderAddress);
		
        order.setOrderStatus("Processing");
		Order savedOrder = orderRepository.save(order);
		

		payment.setOrder(savedOrder);
		// Associate the saved order with the payment
		paymentRepository.save(payment);
 
		// Clear the customer's cart
		cartService.clearCart(customer.getCart().getCartId());
		// Prepare the result DTO
		OrderDto resultDto = new OrderDto();
		BeanUtils.copyProperties(savedOrder, resultDto);
		return resultDto;
	}
 
	/**
	 * Retrieves the details of a specific order.
	 *
	 * @param orderId The ID of the order to retrieve.
	 * @return The DTO representing the order.
	 */
	@Override
	public OrderDto getOrder(Long orderId) {
		return orderRepository.findById(orderId).map(order -> {
			OrderDto dto = new OrderDto();
			BeanUtils.copyProperties(order, dto);
			return dto;
		}).orElse(null);
	}
 
	/**
	 * Retrieves all orders in the system.
	 *
	 * @return A list of order DTOs.
	 */
	@Override
	public List<OrderDto> getAllOrders() {
		return orderRepository.findAll().stream().map(order -> {
			OrderDto dto = new OrderDto();
			BeanUtils.copyProperties(order, dto);
			return dto;
		}).collect(Collectors.toList());
	}
 
	/**
	 * Deletes an order from the system.
	 *
	 * @param orderId The ID of the order to delete.
	 * @return True if the order was successfully deleted, otherwise false.
	 */
	@Override
	public boolean deleteOrder(Long orderId) {
		if (orderRepository.existsById(orderId)) {
			orderRepository.deleteById(orderId);
			return true;
		}
		return false;
	}
 
	/**
	 * Retrieves all orders sorted by order ID.
	 *
	 * @return A list of order DTOs sorted by order ID.
	 */
	@Override
	public List<OrderDto> getAllOrdersByOrderId() {
		return orderRepository.findAll().stream().sorted(Comparator.comparing(Order::getOrderId)).map(order -> {
			OrderDto dto = new OrderDto();
			BeanUtils.copyProperties(order, dto);
			return dto;
		}).collect(Collectors.toList());
	}
 
	/**
	 * Retrieves the total count of distinct orders in the system.
	 *
	 * @return The total count of distinct orders.
	 */
	@Override
	public Long getTotalDistinctOrderCount() {
		return orderRepository.findAll().stream().map(Order::getOrderId).distinct().count();
	}
 
	/**
	 * Updates the status of an order.
	 *
	 * @param orderId   The ID of the order to update.
	 * @param newStatus The new status of the order.
	 * @return The updated order DTO.
	 */
	@Override
	public OrderDto updateOrderStatus(Long orderId, String newStatus) {
 
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
		order.setOrderStatus(newStatus);
		orderRepository.save(order);
		OrderDto orderDto = new OrderDto();
		BeanUtils.copyProperties(order, orderDto);
		return orderDto;
	}
 
	/**
	 * Retrieves all orders sorted by date.
	 *
	 * @return A list of order DTOs sorted by date.
	 */
	public List<OrderDto> getAllOrdersSortedByDate() {
		List<Order> allOrders = orderRepository.findAll();
		if (allOrders.isEmpty()) {
			// If no orders found, return an empty list
			return Collections.emptyList();
		}
		return allOrders.stream().sorted(Comparator.comparing(Order::getOrderDate).reversed()).map(order -> {
			OrderDto orderDto = new OrderDto();
			BeanUtils.copyProperties(order, orderDto);
			return orderDto;
		}).collect(Collectors.toList());
	}
 
	@Override
	public List<OrderDto> getOrdersByCustomerId(Long customerId) {
		 Customer customer = customerRepository.findByCustomerId(customerId)
					.orElseThrow(() -> new ResourceNotFoundException("customer not found with id: " + customerId));
			  List<Order> orders=customer.getOrders();
			  return orders.stream().map(order -> {
					OrderDto dto = new OrderDto();
					BeanUtils.copyProperties(order, dto);
					return dto;
				}).collect(Collectors.toList());
	}
 
}