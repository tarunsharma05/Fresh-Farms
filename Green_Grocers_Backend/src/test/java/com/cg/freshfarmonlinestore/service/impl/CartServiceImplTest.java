package com.cg.freshfarmonlinestore.service.impl;
import static org.assertj.core.api.Assertions.assertThat; // Import for AssertJ assertions
import static org.junit.jupiter.api.Assertions.assertThrows; // Import for JUnit assertions
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
 
import com.cg.freshfarmonlinestore.dto.CartDto;
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.entity.Cart;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Item;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CartRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.ItemRepository;
 
// Test class for CartServiceImpl
class CartServiceImplTest {
 
    // Mocking dependencies
    @Mock
    private CartRepository cartRepository;
 
    @Mock
    private ItemRepository itemRepository;
 
    @Mock
    private CustomerRepository customerRepository;
 
    // Injecting mocks into CartServiceImpl
    @InjectMocks
    private CartServiceImpl cartService;
 
    // Setting up before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
 
    // Test case for adding a cart
    @Test
    void testAddCart() {
        // Arrange
        Cart cart = new Cart();
        CartDto cartDto = new CartDto();
 
        // Mocking the repository method
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
 
        // Act
        CartDto result = cartService.addCart();
 
        // Assert
        verify(cartRepository, times(1)).save(any(Cart.class));
        BeanUtils.copyProperties(cart, cartDto); // Copying properties from entity to DTO
        assertThat(result).isEqualToComparingFieldByField(cartDto); // Asserting result
    }
 
    // Test case for getting cart information
    @Test
    void testGetCart() {
        // Arrange
        List<Customer> customers = new ArrayList<>();
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>()); // Initialize items list
        customer.setCart(cart);
        customers.add(customer);
 
        // Mocking the repository method
        when(customerRepository.findAll()).thenReturn(customers);
 
        // Act
        List<CartDto> result = cartService.getCart();
 
        // Assert
        assertThat(result).hasSize(1); // Asserting size of the result list
        verify(customerRepository, times(1)).findAll(); // Verifying method invocation
    }
 
    // Test case for getting cart by customer ID (success scenario)
    @Test
    void testGetCartByCustomerId_Success() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>()); // Initialize items list
        customer.setCart(cart);
 
        // Mocking the repository method
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Act
        CartDto result = cartService.getCartByCustomerId(1L);
 
        // Assert
        assertThat(result).isNotNull(); // Asserting non-null result
        assertThat(result.getItems()).isEmpty(); // Ensure items list is empty
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
    }
 
    
 // Test case for removing an item from cart (customer not found)
    @Test
    void testRemoveItemFromCart_CustomerNotFound() {
        // Mocking repository method
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(1L, 1L));
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
    }
 
    // Test case for removing an item from cart (cart not found)
    @Test
    void testRemoveItemFromCart_CartNotFound() {
        // Arrange
        Customer customer = new Customer();
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(1L, 1L));
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
    }
 
    // Test case for removing an item from cart (item not found)
    @Test
    void testRemoveItemFromCart_ItemNotFound() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setItemId(2L);
        items.add(item);
        cart.setItems(items);
        customer.setCart(cart);
 
        // Mocking repository method
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(1L, 1L));
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
    }
    
    @Test
    void testAddItemsToCart_CustomerNotFound() {
        // Mocking repository method to return empty optional
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemsToCart(1L, 1L));
 
        // Verifying method invocation
        verify(customerRepository, times(1)).findByCustomerId(1L);
    }
    @Test
    void testAddItemsToCart_ItemNotFound() {
        // Mocking repository method to return empty optional
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(new Customer()));
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.addItemsToCart(1L, 1L));
 
        // Verifying method invocation
        verify(customerRepository, times(1)).findByCustomerId(1L);
        verify(itemRepository, times(1)).findById(1L);
    }
    @Test
    void testRemoveItemFromCart_ItemNotFoundInCart() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setItemId(2L); // Different item ID
        items.add(item);
        cart.setItems(items);
        customer.setCart(cart);
 
        // Mocking repository method
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.removeItemFromCart(1L, 1L));
 
        // Verifying method invocation
        verify(customerRepository, times(1)).findByCustomerId(1L);
    }
    @Test
    void testRecalculateTotalAmount_CartNotFound() {
        // Mocking repository method to return empty optional
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.recalculateTotalAmount(1L));
 
        // Verifying method invocation
        verify(cartRepository, times(1)).findById(1L);
    }
    
    // Test case for getting cart by customer ID (customer not found)
    @Test
    void testGetCartByCustomerId_CustomerNotFound() {
        // Mocking the repository method to return empty optional
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.empty());
 
        // Asserting that the method throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> cartService.getCartByCustomerId(1L));
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
    }
 
    // Test case for adding items to cart (success scenario)
    @Test
    void testAddItemsToCart_Success() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>());
        customer.setCart(cart);
 
        Item item = new Item();
        item.setItemId(1L);
        item.setItemName("Sample Item");
        item.setItemPrice(10.0);
 
        // Mocking repository methods
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);
 
        // Act
        ItemDto result = cartService.addItemsToCart(1L, 1L);
 
        // Assert
        assertThat(result).isNotNull(); // Asserting non-null result
        assertThat(result.getItemId()).isEqualTo(1L); // Asserting item ID
        assertThat(cart.getItems()).hasSize(1); // Asserting cart items size
        assertThat(cart.getItems().get(0).getItemId()).isEqualTo(1L); // Asserting cart item ID
        assertThat(cart.getTotalAmount()).isEqualTo(10.0); // Asserting cart total amount
 
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
        verify(itemRepository, times(1)).findById(1L); // Verifying method invocation
        verify(cartRepository, times(1)).save(cart); // Verifying method invocation
    }
 
    // Test case for clearing the cart (success scenario)
    @Test
    void testClearCart_Success() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        cart.setItems(new ArrayList<>()); // Initialize items list
        customer.setCart(cart);
 
        // Mocking repository method
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
 
        // Act
        String result = cartService.clearCart(1L);
 
        // Assert
        assertThat(result).isEqualTo("Cart is empty now"); // Asserting success message
        assertThat(cart.getItems()).isEmpty(); // Ensure cart is empty
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
        verify(cartRepository, times(1)).save(cart); // Verifying method invocation
    }
 
    // Test case for removing an item from cart (success scenario)
    @Test
    void testRemoveItemFromCart_Success() {
        // Arrange
        Customer customer = new Customer();
        Cart cart = new Cart();
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setItemId(1L);
        items.add(item);
        cart.setItems(items);
        customer.setCart(cart);
 
        // Mocking repository methods
        when(customerRepository.findByCustomerId(1L)).thenReturn(Optional.of(customer));
        when(cartRepository.save(cart)).thenReturn(cart);
 
        // Act
        String result = cartService.removeItemFromCart(1L, 1L);
 
        //
 
 
        // Assert
        assertThat(result).isEqualTo("Item removed from cart successfully."); // Asserting success message
        verify(customerRepository, times(1)).findByCustomerId(1L); // Verifying method invocation
        verify(cartRepository, times(1)).save(cart); // Verifying method invocation
    }
 
    
    
    
}