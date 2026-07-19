package com.cg.freshfarmonlinestore.controller;
import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
class ItemControllerTest {
    @Mock
    private ItemService itemService;
    @InjectMocks
    private ItemController itemController;
    private ItemDto itemDto;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        itemDto = new ItemDto();
        itemDto.setItemId(1L);
        itemDto.setItemName("Apple");
        itemDto.setItemType("Fruit");
        itemDto.setItemPrice(100.0);
    }
    @Test
    void testAllItem_Positive() {
        List<ItemDto> itemList = Arrays.asList(itemDto);
        when(itemService.allItem()).thenReturn(itemList);
        ResponseEntity<List<ItemDto>> response = itemController.allItem();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(itemDto.getItemId(), response.getBody().get(0).getItemId());
    }
    @Test
    void testAllItem_Empty() {
        when(itemService.allItem()).thenReturn(Collections.emptyList());
        ResponseEntity<List<ItemDto>> response = itemController.allItem();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
    @Test
    void testGetItemById_Positive() {
        when(itemService.getItemById(anyLong())).thenReturn(itemDto);
        ResponseEntity<ItemDto> response = itemController.getItemById(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDto.getItemId(), response.getBody().getItemId());
    }
    @Test
    void testGetItemById_Negative() {
        when(itemService.getItemById(anyLong())).thenThrow(new ResourceNotFoundException("Item not found"));
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> itemController.getItemById(1L));
        assertEquals("Item not found", exception.getMessage());
    }
    @Test
    void testGetItemByNameOrItemByType_Positive() {
        List<ItemDto> itemList = Arrays.asList(itemDto);
        when(itemService.getItemByNameOrItemByType(anyString())).thenReturn(itemList);
        ResponseEntity<List<ItemDto>> response = itemController.getItemByNameOrItemByType("Apple");
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(itemDto.getItemId(), response.getBody().get(0).getItemId());
    }
    @Test
    void testAddItem_Positive() {
        when(itemService.addItem(any(ItemDto.class))).thenReturn(itemDto);
        ResponseEntity<ItemDto> response = itemController.addItem(itemDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(itemDto.getItemId(), response.getBody().getItemId());
    }
    @Test
    void testDeleteItem_Positive() {
        when(itemService.deleteItem(anyLong())).thenReturn("Item deleted successfully");
        ResponseEntity<String> response = itemController.deleteItem(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Item deleted successfully", response.getBody());
    }
    @Test
    void testDeleteItem_Negative() {
        when(itemService.deleteItem(anyLong())).thenReturn("Item not found");
        ResponseEntity<String> response = itemController.deleteItem(1L);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Item not found", response.getBody());
    }
    @Test
    void testUpdateItem_Positive() {
        when(itemService.updateItem(anyLong(), any(ItemDto.class))).thenReturn(itemDto);
        ResponseEntity<ItemDto> response = itemController.updateItem(1L, itemDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(itemDto.getItemId(), response.getBody().getItemId());
    }
    @Test
    void testUpdateItem_Negative() {
        when(itemService.updateItem(anyLong(), any(ItemDto.class))).thenReturn(null);
        ResponseEntity<ItemDto> response = itemController.updateItem(1L, itemDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    @Test
    void testSortItemByPrice_Positive() {
        List<ItemDto> itemList = Arrays.asList(itemDto);
        when(itemService.sortItemByPrice()).thenReturn(itemList);
        ResponseEntity<List<ItemDto>> response = itemController.sortItemByPrice();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(itemDto.getItemId(), response.getBody().get(0).getItemId());
    }
    @Test
    void testSortItemByName_Positive() {
        List<ItemDto> itemList = Arrays.asList(itemDto);
        when(itemService.sortItemByName()).thenReturn(itemList);
        ResponseEntity<List<ItemDto>> response = itemController.sortItemByName();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(itemDto.getItemId(), response.getBody().get(0).getItemId());
    }
    @Test
    void testGetItemByNameOrItemByType_Empty() {
        when(itemService.getItemByNameOrItemByType(anyString())).thenReturn(Collections.emptyList());
 
        ResponseEntity<List<ItemDto>> response = itemController.getItemByNameOrItemByType("NonExistentName");
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
 
    @Test
    void testAddItem_Invalid() {
        ItemDto invalidItemDto = new ItemDto();
        invalidItemDto.setItemId(-1L); // Invalid ID
 
        when(itemService.addItem(any(ItemDto.class))).thenThrow(new IllegalArgumentException("Invalid item details"));
 
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> itemController.addItem(invalidItemDto));
 
        assertEquals("Invalid item details", exception.getMessage());
    }
 
    @Test
    void testDeleteItem_ItemNotFound() {
        when(itemService.deleteItem(anyLong())).thenThrow(new ResourceNotFoundException("Item not found"));
 
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> itemController.deleteItem(99L));
 
        assertEquals("Item not found", exception.getMessage());
    }
 
    @Test
    void testUpdateItem_ItemNotFound() {
        when(itemService.updateItem(anyLong(), any(ItemDto.class))).thenThrow(new ResourceNotFoundException("Item not found"));
 
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> itemController.updateItem(99L, itemDto));
 
        assertEquals("Item not found", exception.getMessage());
    }
 
    @Test
    void testSortItemByPrice_Empty() {
        when(itemService.sortItemByPrice()).thenReturn(Collections.emptyList());
 
        ResponseEntity<List<ItemDto>> response = itemController.sortItemByPrice();
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
 
    @Test
    void testSortItemByName_Empty() {
        when(itemService.sortItemByName()).thenReturn(Collections.emptyList());
 
        ResponseEntity<List<ItemDto>> response = itemController.sortItemByName();
 
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, response.getBody().size());
    }
}