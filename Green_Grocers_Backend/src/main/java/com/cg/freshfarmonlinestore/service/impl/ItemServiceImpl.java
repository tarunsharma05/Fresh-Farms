package com.cg.freshfarmonlinestore.service.impl;
 
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
 
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.entity.Cart;
import com.cg.freshfarmonlinestore.entity.Item;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CartRepository;
import com.cg.freshfarmonlinestore.repository.ItemRepository;
import com.cg.freshfarmonlinestore.service.CartService;
import com.cg.freshfarmonlinestore.service.ItemService;
 
@Service
public class ItemServiceImpl implements ItemService {
 
    @Autowired
    private ItemRepository itemRepository;
 
    @Autowired
    private CartRepository cartRepository;
 
    @Autowired
    private CartService cartService;
 
    /**
     * Retrieves all items from the repository and converts them to a list of ItemDto.
     *
     * @return List of ItemDto containing all items.
     */
    @Override
    public List<ItemDto> allItem() {
        List<Item> itemsList = itemRepository.findAll();
        List<ItemDto> itemsDtoList = new ArrayList<>();
        // Converting item entity list to itemDTO list
        itemsDtoList = itemsList.stream().map(i -> {
            ItemDto itemDto = new ItemDto();
            BeanUtils.copyProperties(i, itemDto);
            return itemDto;
        }).collect(Collectors.toList());
        return itemsDtoList;
    }
 
    /**
     * Retrieves an item by its ID and converts it to ItemDto.
     *
     * @param itemId The ID of the item to retrieve.
     * @return The ItemDto of the retrieved item.
     * @throws ResourceNotFoundException if the item with the given ID is not found.
     */
    @Override
    public ItemDto getItemById(long itemId) {
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
        ItemDto itemDto = new ItemDto();
        BeanUtils.copyProperties(item, itemDto);
        return itemDto;
    }
 
    /**
     * Retrieves items by their name or type (partial match) and converts them to a list of ItemDto.
     *
     * @param itemNameOrType The name or type to search for.
     * @return List of ItemDto matching the search criteria.
     */
    @Override
    public List<ItemDto> getItemByNameOrItemByType(String itemNameOrType) {
        List<Item> itemsList = itemRepository
                .findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase(itemNameOrType, itemNameOrType);
        List<ItemDto> itemDtoList = itemsList.stream().map(i -> {
            ItemDto itemDto = new ItemDto();
            BeanUtils.copyProperties(i, itemDto);
            return itemDto;
        }).collect(Collectors.toList());
        return itemDtoList;
    }
 
    /**
     * Adds a new item to the repository and returns the saved item as ItemDto.
     *
     * @param itemDto The ItemDto containing item details to add.
     * @return The ItemDto of the saved item.
     */
    @Override
    public ItemDto addItem(ItemDto itemDto) {
        Item item = new Item();
        BeanUtils.copyProperties(itemDto, item);
        Item savedItem = itemRepository.save(item);
        BeanUtils.copyProperties(savedItem, itemDto);
        return itemDto;
    }
 
    /**
     * Deletes an item by its ID and removes it from all carts. 
     * Returns a success message upon successful deletion.
     *
     * @param itemId The ID of the item to delete.
     * @return A success message with the item name and ID.
     * @throws ResourceNotFoundException if the item with the given ID is not found.
     */
    @Override
    public String deleteItem(long itemId) {
        Item deletedItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
        ItemDto deletedItemDto = new ItemDto();
        BeanUtils.copyProperties(deletedItem, deletedItemDto);
        if (deletedItem != null) {
            List<Cart> carts = cartRepository.findAll();
            for (Cart cart : carts) {
                while (cart.getItems().contains(deletedItem)) {
                    cartService.removeItemFromCart(cart.getCartId(), itemId);
                    cartRepository.save(cart);
                }
            }
            itemRepository.deleteById(itemId);
        }
        return deletedItem.getItemName() + " deleted successfully with id " + itemId;
    }
 
    /**
     * Updates an existing item by its ID and returns the updated item as ItemDto.
     * Also updates the total amount of all carts containing this item.
     *
     * @param itemId The ID of the item to update.
     * @param itemDto The ItemDto containing updated item details.
     * @return The ItemDto of the updated item.
     * @throws ResourceNotFoundException if the item with the given ID is not found.
     */
    @Override
    public ItemDto updateItem(long itemId, ItemDto itemDto) {
        Item updatedItem = itemRepository.findById(itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with id: " + itemId));
        BeanUtils.copyProperties(itemDto, updatedItem);
        updatedItem.setItemId(itemId);
        itemDto.setItemId(itemId);
        itemRepository.save(updatedItem);
        // Update the total amount of all carts containing this item
        List<Cart> carts = cartRepository.findAll();
        for (Cart cart : carts) {
            if (cart.getItems().contains(updatedItem)) {
                cartService.recalculateTotalAmount(cart.getCartId());
                cartRepository.save(cart);
            }
        }
        return itemDto;
    }
 
    /**
     * Retrieves all items sorted by price and converts them to a list of ItemDto.
     *
     * @return List of ItemDto sorted by item price.
     */
    @Override
    public List<ItemDto> sortItemByPrice() {
        List<Item> itemsByPrice = itemRepository.findByOrderByItemPrice();
        List<ItemDto> itemsDtoByPrice = itemsByPrice.stream().map(i -> {
            ItemDto itemDto = new ItemDto();
            BeanUtils.copyProperties(i, itemDto);
            return itemDto;
        }).collect(Collectors.toList());
        return itemsDtoByPrice;
    }
 
    /**
     * Retrieves all items sorted by name and converts them to a list of ItemDto.
     *
     * @return List of ItemDto sorted by item name.
     */
    @Override
    public List<ItemDto> sortItemByName() {
        List<Item> itemsByName = itemRepository.findByOrderByItemName();
        List<ItemDto> itemsDtoByName = itemsByName.stream().map(i -> {
            ItemDto itemDto = new ItemDto();
            BeanUtils.copyProperties(i, itemDto);
            return itemDto;
        }).collect(Collectors.toList());
        return itemsDtoByName;
    }
}