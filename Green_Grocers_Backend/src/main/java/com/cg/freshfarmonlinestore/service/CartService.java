 
package com.cg.freshfarmonlinestore.service;
 
import java.util.List;
import com.cg.freshfarmonlinestore.dto.CartDto;
import com.cg.freshfarmonlinestore.dto.ItemDto;
 
/**
* Service interface for managing shopping carts.
*/
public interface CartService {
 
    // Method to add a new cart
    CartDto addCart();
    
    // Method to retrieve all carts
    List<CartDto> getCart();
    
    // Method to retrieve the cart by customer ID
    CartDto getCartByCustomerId(long customerId);
    
    // Method to add items to the cart
    ItemDto addItemsToCart(long customerId, long itemId);
    
    // Method to clear the cart
    String clearCart(long customerId);
    
    // Method to remove an item from the cart
    String removeItemFromCart(long customerId, long itemId);
    
    // Method to recalculate the total amount in the cart
    void recalculateTotalAmount(long cartId);
    
}
 