package com.cg.freshfarmonlinestore.service;

import java.util.List;
import com.cg.freshfarmonlinestore.dto.ItemDto;

/**
 * Service interface for managing items.
 */
public interface ItemService {
    
    // Add an item
    public ItemDto addItem(ItemDto itemDto);

    // Delete an item by ID
    public String deleteItem(long itemId);

    // Update an item
    public ItemDto updateItem(long itemId, ItemDto itemDto);

    // Get all items
    public List<ItemDto> allItem();

    // Get an item by ID
    public ItemDto getItemById(long itemId);

    // Get items by name or type
    public List<ItemDto> getItemByNameOrItemByType(String itemNameOrType);

    // Sort items by price
    public List<ItemDto> sortItemByPrice();

    // Sort items by name
    public List<ItemDto> sortItemByName();
}
