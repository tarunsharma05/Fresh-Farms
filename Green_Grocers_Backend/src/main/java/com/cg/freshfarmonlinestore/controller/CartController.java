package com.cg.freshfarmonlinestore.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cg.freshfarmonlinestore.dto.CartDto;
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.service.CartService;
/**
* Controller class for managing shopping carts.
*/
@RestController
@CrossOrigin("*")
@RequestMapping("/api/carts")
public class CartController {
    @Autowired
    private CartService cartService;
    /**
     * Endpoint to get all carts.
     * 
     * @return List of all CartDto.
     */
    @GetMapping
    public List<CartDto> getCart() {
        return cartService.getCart();
    }
    /**
     * Endpoint to add an item to the cart using customerId and itemId.
     * 
     * @param customerId The ID of the customer.
     * @param itemId The ID of the item to be added.
     * @return The added ItemDto.
     */
    @PostMapping("/additemtocart/{customerId}/{itemId}")
    public ItemDto addItemsToCart(@PathVariable("customerId") long customerId, @PathVariable("itemId") long itemId) {
        return cartService.addItemsToCart(customerId, itemId);
    }
    /**
     * Endpoint to clear the cart using customerId.
     * 
     * @param customerId The ID of the customer.
     * @return A message indicating the cart is cleared.
     */
    @PutMapping("/clear-cart/{customerId}")
    public String clearCart(@PathVariable("customerId") long customerId) {
        return cartService.clearCart(customerId);
    }
    /**
     * Endpoint to get the cart by customerId.
     * 
     * @param customerId The ID of the customer.
     * @return The CartDto of the specified customer.
     */
    @GetMapping("/{customerId}")
    public CartDto getCartByCustomerId(@PathVariable("customerId") long customerId) {
        return cartService.getCartByCustomerId(customerId);
    }
    /**
     * Endpoint to remove an item from the cart using customerId and itemId.
     * 
     * @param customerId The ID of the customer.
     * @param itemId The ID of the item to be removed.
     * @return A message indicating the item was removed successfully.
     */
    @PutMapping("/removeitemfromcart/{customerId}/{itemId}")
    public String removeItemFromCart(@PathVariable("customerId") long customerId, @PathVariable("itemId") long itemId) {
        return cartService.removeItemFromCart(customerId, itemId);
    }

}