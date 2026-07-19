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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/items")
@CrossOrigin("*")
public class ItemController {
    @Autowired
    private ItemService itemService;

    /**
     * Get all items.
     * This method handles GET requests to "/api/items".
     * It retrieves a list of all items and returns them in the response body
     * with an HTTP status of OK.
     * 
     * @return ResponseEntity containing the list of items and HTTP status OK
     */
    @GetMapping
    public ResponseEntity<List<ItemDto>> allItem() {
        List<ItemDto> items = itemService.allItem();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Get item by ID.
     * This method handles GET requests to "/api/items/itemid/{itemId}".
     * It retrieves an item by its ID and returns it in the response body
     * with an HTTP status of OK.
     * 
     * @param itemId The ID of the item to retrieve
     * @return ResponseEntity containing the item and HTTP status OK
     */
    @GetMapping("/itemid/{itemId}")
    public ResponseEntity<ItemDto> getItemById(@PathVariable("itemId") long itemId) {
        ItemDto item = itemService.getItemById(itemId);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * Get items by name or type.
     * This method handles GET requests to "/api/items/itembynameortype/{itemNameOrType}".
     * It retrieves a list of items that match the given name or type and returns them
     * in the response body with an HTTP status of OK.
     * 
     * @param itemNameOrType The name or type of the items to retrieve
     * @return ResponseEntity containing the list of items and HTTP status OK
     */
    @GetMapping("/itembynameortype/{itemNameOrType}")
    public ResponseEntity<List<ItemDto>> getItemByNameOrItemByType(
            @PathVariable("itemNameOrType") String itemNameOrType) {
        List<ItemDto> items = itemService.getItemByNameOrItemByType(itemNameOrType);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Add a new item.
     * This method handles POST requests to "/api/items".
     * It creates a new item using the provided item details and returns the saved item
     * in the response body with an HTTP status of CREATED.
     * 
     * @param itemDto The details of the item to add
     * @return ResponseEntity containing the saved item and HTTP status CREATED
     */
    @PostMapping
    public ResponseEntity<ItemDto> addItem(@RequestBody @Valid ItemDto itemDto) {
        ItemDto newItem = itemService.addItem(itemDto);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    /**
     * Delete an item by ID.
     * This method handles DELETE requests to "/api/items/deleteitem/{itemId}".
     * It deletes the item with the given ID and returns a response message
     * with an HTTP status of OK.
     * 
     * @param itemId The ID of the item to delete
     * @return ResponseEntity containing the response message and HTTP status OK
     */
    @DeleteMapping("/deleteitem/{itemId}")
    public ResponseEntity<String> deleteItem(@PathVariable("itemId") long itemId) {
        String response = itemService.deleteItem(itemId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Update an existing item.
     * This method handles PUT requests to "/api/items/updateitem/{itemId}".
     * It updates the item with the given ID using the provided item details,
     * and returns the updated item in the response body with an HTTP status of OK.
     * If the item cannot be updated, it returns an HTTP status of BAD_REQUEST.
     * 
     * @param itemId The ID of the item to update
     * @param itemDto The updated item details
     * @return ResponseEntity containing the updated item and HTTP status OK, 
     *         or HTTP status BAD_REQUEST if the update fails
     */
    @PutMapping("/updateitem/{itemId}")
    public ResponseEntity<ItemDto> updateItem(@PathVariable("itemId") long itemId,
            @Valid @RequestBody ItemDto itemDto) {
        ItemDto updatedItem = itemService.updateItem(itemId, itemDto);
        if (updatedItem == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    /**
     * Sort items by price.
     * This method handles GET requests to "/api/items/sortbyprice".
     * It retrieves a list of items sorted by their price and returns them
     * in the response body with an HTTP status of OK.
     * 
     * @return ResponseEntity containing the list of items sorted by price and HTTP status OK
     */
    @GetMapping("/sortbyprice")
    public ResponseEntity<List<ItemDto>> sortItemByPrice() {
        List<ItemDto> items = itemService.sortItemByPrice();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    /**
     * Sort items by name.
     * This method handles GET requests to "/api/items/sortbyname".
     * It retrieves a list of items sorted by their name and returns them
     * in the response body with an HTTP status of OK.
     * 
     * @return ResponseEntity containing the list of items sorted by name and HTTP status OK
     */
    @GetMapping("/sortbyname")
    public ResponseEntity<List<ItemDto>> sortItemByName() {
        List<ItemDto> items = itemService.sortItemByName();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
