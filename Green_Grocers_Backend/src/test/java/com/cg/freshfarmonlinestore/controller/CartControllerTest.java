package com.cg.freshfarmonlinestore.controller;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
 
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
 
import com.cg.freshfarmonlinestore.dto.CartDto;
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.service.CartService;
 
/**
* Test class for CartController.
*/
class CartControllerTest {
 
    // Mocking the CartService to isolate the controller for testing
    @Mock
    private CartService cartService;
 
    // Injecting mock dependencies into CartController
    @InjectMocks
    private CartController cartController;
 
    // Sample CartDto object for testing
    private CartDto cartDto;
 
    // Setting up before each test
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
 
        // Creating a sample CartDto object
        cartDto = new CartDto();
        cartDto.setCartId(1L);
        cartDto.setEntryDate(LocalDate.now());
        cartDto.setItemCount(1);
        cartDto.setTotalAmount(100.0);
        cartDto.setItems(Collections.singletonList(new ItemDto(1L, "Sample Item", 100.0, "Type", "url", "desc", "qty")));
    }
 
    /**
     * Test case for retrieving cart information (positive scenario).
     */
    @Test
    void testGetCart_Positive() {
        // Mocking the cartService to return a list containing the sample CartDto object
        when(cartService.getCart()).thenReturn(Collections.singletonList(cartDto));
 
        // Invoking the controller method to get cart information
        List<CartDto> result = cartController.getCart();
 
        // Asserting that the returned list contains one element and has the expected cartId
        assertEquals(1, result.size());
        assertEquals(cartDto.getCartId(), result.get(0).getCartId());
    }
 
    /**
     * Test case for retrieving cart information (negative scenario).
     */
    @Test
    void testGetCart_Negative() {
        // Mocking the cartService to throw a ResourceNotFoundException
        when(cartService.getCart()).thenThrow(new ResourceNotFoundException("No carts found"));
 
        // Asserting that the controller method throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> cartController.getCart());
    }
 
    /**
     * Test case for adding items to cart (positive scenario).
     */
    @Test
    void testAddItemsToCart_Positive() {
        // Mocking the cartService to return the first item in the sample CartDto object
        when(cartService.addItemsToCart(anyLong(), anyLong())).thenReturn(cartDto.getItems().get(0));
 
        // Invoking the controller method to add items to cart
        ItemDto result = cartController.addItemsToCart(1L, 1L);
 
        // Asserting that the returned item matches the expected item
        assertEquals(cartDto.getItems().get(0).getItemId(), result.getItemId());
        assertEquals(cartDto.getItems().get(0).getItemName(), result.getItemName());
    }
 
    /**
     * Test case for adding items to cart (negative scenario).
     */
    @Test
    void testAddItemsToCart_Negative() {
        // Mocking the cartService to throw a ResourceNotFoundException
        when(cartService.addItemsToCart(anyLong(), anyLong())).thenThrow(new ResourceNotFoundException("Customer not found"));
 
        // Asserting that the controller method throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> cartController.addItemsToCart(1L, 1L));
    }
 
    /**
     * Test case for clearing the cart (positive scenario).
     */
    @Test
    void testClearCart_Positive() {
        // Mocking the cartService to return a success message when clearing the cart
        when(cartService.clearCart(anyLong())).thenReturn("Cart is empty now");
 
        // Invoking the controller method to clear the cart
        String result = cartController.clearCart(1L);
 
        // Asserting that the returned message matches the expected message
        assertEquals("Cart is empty now", result);
    }
 
    /**
     * Test case for clearing the cart (negative scenario).
     */
    @Test
    void testClearCart_Negative() {
        // Mocking the cartService to throw a ResourceNotFoundException
        when(cartService.clearCart(anyLong())).thenThrow(new ResourceNotFoundException("Cart not found"));
 
        // Asserting that the controller method throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> cartController.clearCart(1L));
    }
 
    /**
     * Test case for retrieving cart by customer ID (positive scenario).
     */
    @Test
    void testGetCartByCustomerId_Positive() {
        // Mocking the cartService to return the sample CartDto object
        when(cartService.getCartByCustomerId(anyLong())).thenReturn(cartDto);
 
        // Invoking the controller method to get cart by customer ID
        CartDto result = cartController.getCartByCustomerId(1L);
 
        // Asserting that the returned cart matches the expected cart
        assertEquals(cartDto.getCartId(), result.getCartId());
    }
 
    /**
     * Test case for retrieving cart by customer ID (negative scenario).
     */
    @Test
    void testGetCartByCustomerId_Negative() {
        // Mocking the cartService to throw a ResourceNotFoundException
        when(cartService.getCartByCustomerId(anyLong())).thenThrow(new ResourceNotFoundException("Customer not found"));
 
        // Asserting that the controller method throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> cartController.getCartByCustomerId(1L));
    }
 
    /**
     * Test case for removing an item from cart (positive scenario).
     */
    @Test
    void testRemoveItemFromCart_Positive() {
        // Mocking the cartService to return a success message when removing an item
        when(cartService.removeItemFromCart(anyLong(), anyLong())).thenReturn("Item removed from cart successfully.");
 
        // Invoking the controller method to remove an item from cart
        String result = cartController.removeItemFromCart(1L, 1L);
 
        // Asserting that the returned message matches the expected message
        assertEquals("Item removed from cart successfully.", result);
    }
 
    /**
     * Test case for removing an item from cart (negative scenario).
     */
    @Test
    void testRemoveItemFromCart_Negative() {
        // Mocking the cartService to throw a ResourceNotFoundException
        when(cartService.removeItemFromCart(anyLong(), anyLong())).thenThrow(new ResourceNotFoundException("Item not found"));
 
        // Asserting that the controller method throws the expected exception
        assertThrows(ResourceNotFoundException.class, () -> cartController.removeItemFromCart(1L, 1L));
    }
}