package com.cg.freshfarmonlinestore.service.impl;
 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import com.cg.freshfarmonlinestore.dto.CartDto;
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.entity.Cart;
import com.cg.freshfarmonlinestore.entity.Customer;
import com.cg.freshfarmonlinestore.entity.Item;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CartRepository;
import com.cg.freshfarmonlinestore.repository.CustomerRepository;
import com.cg.freshfarmonlinestore.repository.ItemRepository;
import com.cg.freshfarmonlinestore.service.CartService;
import lombok.AllArgsConstructor;
 
/**
* Implementation of CartService interface.
*/
@Service
@AllArgsConstructor
public class CartServiceImpl implements CartService {
 
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    private final CustomerRepository customerRepository;
 
    /**
     * Method to add a new cart.
     *
     * @return The created CartDto.
     */
    @Override
    public CartDto addCart() {
        // Create and save a new Cart entity
        Cart cart = new Cart();
        cartRepository.save(cart);
        // Copy properties from Cart entity to CartDto
        CartDto cartDto = new CartDto();
        BeanUtils.copyProperties(cart, cartDto);
        return cartDto;
    }
 
    /**
     * Method to retrieve all carts.
     *
     * @return List of CartDto.
     */
    @Override
    public List<CartDto> getCart() {
        // Retrieve all customers and their carts
        List<Customer> customers = customerRepository.findAll();
        List<CartDto> cartDtoList = new ArrayList<>();
 
        for (Customer customer : customers) {
            Cart cart = customer.getCart();
            if (cart != null) {
                CartDto cartDto = new CartDto();
                BeanUtils.copyProperties(cart, cartDto);
 
                // Copy item properties to ItemDto
                List<ItemDto> itemDtoList = cart.getItems().stream().map(item -> {
                    ItemDto itemDto = new ItemDto();
                    BeanUtils.copyProperties(item, itemDto);
                    return itemDto;
                }).collect(Collectors.toList());
 
                cartDto.setItems(itemDtoList);
                cartDtoList.add(cartDto);
            }
        }
 
        return cartDtoList;
    }
 
    /**
     * Method to retrieve the cart by customer ID.
     *
     * @param customerId The ID of the customer.
     * @return The CartDto associated with the customer.
     */
    @Override
    public CartDto getCartByCustomerId(long customerId) {
        // Find customer by ID or throw an exception if not found
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        // Get the cart associated with the customer
        Cart cart = customer.getCart();
        if (cart != null) {
            CartDto cartDto = new CartDto();
            BeanUtils.copyProperties(cart, cartDto);
 
            // Copy item properties to ItemDto
            List<ItemDto> itemDtoList = cart.getItems().stream().map(item -> {
                ItemDto itemDto = new ItemDto();
                BeanUtils.copyProperties(item, itemDto);
                return itemDto;
            }).collect(Collectors.toList());
 
            cartDto.setItems(itemDtoList);
            return cartDto;
        } else {
            throw new ResourceNotFoundException("Cart not found for customer with ID: " + customerId);
        }
    }
 
    /**
     * Method to add items to the cart.
     *
     * @param customerId The ID of the customer.
     * @param itemId     The ID of the item to be added.
     * @return The ItemDto representing the added item.
     */
    @Override
    public ItemDto addItemsToCart(long customerId, long itemId) {
        // Find customer by ID or throw an exception if not found
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        // Get or create the cart associated with the customer
        Cart cart = customer.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setCustomer(customer);
            customer.setCart(cart);
        }
 
        // Find item by ID or throw an exception if not found
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + itemId));
 
        // Add item to cart and update item count and total amount
        cart.getItems().add(item);
        cart.setItemCount(cart.getItems().size());
        cart.setTotalAmount(cart.getItems().stream().mapToDouble(Item::getItemPrice).sum());
 
        // Save the updated cart
        cartRepository.save(cart);
 
        // Copy item properties to ItemDto
        ItemDto itemDto = new ItemDto();
        BeanUtils.copyProperties(item, itemDto);
        return itemDto;
    }
 
    /**
     * Method to clear the cart.
     *
     * @param customerId The ID of the customer.
     * @return A message indicating that the cart is now empty.
     */
    @Override
    public String clearCart(long customerId) {
        // Find customer by ID or throw an exception if not found
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        // Get the cart associated with the customer or throw an exception if not found
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with ID: " + customerId);
        }
 
        // Clear items in the cart and update item count and total amount
        cart.getItems().clear();
        cart.setItemCount(0);
        cart.setTotalAmount(0);
 
        // Save the updated cart
        cartRepository.save(cart);
        return "Cart is empty now";
    }
 
    /**
     * Method to remove an item from the cart.
     *
     * @param customerId The ID of the customer.
     * @param itemId     The ID of the item to be removed.
     * @return A message indicating that the item was removed successfully.
     */
    @Override
    public String removeItemFromCart(long customerId, long itemId) {
        // Find customer by ID or throw an exception if not found
        Customer customer = customerRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        // Get the cart associated with the customer or throw an exception if not found
        Cart cart = customer.getCart();
        if (cart == null) {
            throw new ResourceNotFoundException("Cart not found for customer with ID: " + customerId);
        }
 
        // Remove item from the cart and update item count and total amount
        List<Item> items = cart.getItems();
        for (Iterator<Item> iterator = items.iterator(); iterator.hasNext();) {
            Item item = iterator.next();
            if (item.getItemId() == itemId) {
                iterator.remove();
                cart.setItemCount(items.size());
                cart.setTotalAmount(items.stream().mapToDouble(Item::getItemPrice).sum());
                cartRepository.save(cart);
                return "Item removed from cart successfully.";
            }
        }
 
        // Throw an exception if item not found in the cart
        throw new ResourceNotFoundException("Item not found in the cart with id: " + itemId);
    }
 
    /**
     * Method to recalculate the total amount in the cart.
     *
     * @param cartId The ID of the cart.
     */
    @Override
    public void recalculateTotalAmount(long cartId) {
        // Find cart by ID or throw an exception if not found
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart not found with ID: " + cartId));
        // Recalculate total amount and update the cart
        double totalAmount = cart.getItems().stream().mapToDouble(Item::getItemPrice).sum();
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }
    
}