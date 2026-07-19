package com.cg.freshfarmonlinestore.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cg.freshfarmonlinestore.dto.ItemDto;
import com.cg.freshfarmonlinestore.entity.Item;
import com.cg.freshfarmonlinestore.exception.ResourceNotFoundException;
import com.cg.freshfarmonlinestore.repository.CartRepository;
import com.cg.freshfarmonlinestore.repository.ItemRepository;
import com.cg.freshfarmonlinestore.service.CartService;

public class ItemServiceImplTest {

	@InjectMocks
	private ItemServiceImpl itemService;

	@Mock
	private ItemRepository itemRepository;

	@Mock
	private CartRepository cartRepository;

	@Mock
	private CartService cartService;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testAllItem_Positive() {
		// Given
		List<Item> items = Arrays.asList(new Item(), new Item());
		when(itemRepository.findAll()).thenReturn(items);

		// When
		List<ItemDto> result = itemService.allItem();

		// Then
		assertEquals(2, result.size());
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testAllItem_Negative() {
		// Given
		when(itemRepository.findAll()).thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.allItem();

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findAll();
	}

	@Test
	public void testGetItemById_Positive() {
		// Given
		Item item = new Item();
		item.setItemId(1L);
		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

		// When
		ItemDto result = itemService.getItemById(1L);

		// Then
		assertNotNull(result);
		assertEquals(1L, result.getItemId());
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetItemById_Negative() {
		// Given
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ResourceNotFoundException.class, () -> {
			itemService.getItemById(1L);
		});
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	public void testGetItemByNameOrItemByType_Positive() {
		// Given
		List<Item> items = Arrays.asList(new Item(), new Item());
		when(itemRepository.findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("name", "name"))
				.thenReturn(items);

		// When
		List<ItemDto> result = itemService.getItemByNameOrItemByType("name");

		// Then
		assertEquals(2, result.size());
		verify(itemRepository, times(1)).findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("name",
				"name");
	}

	@Test
	public void testGetItemByNameOrItemByType_Negative() {
		// Given
		when(itemRepository.findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("name", "name"))
				.thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.getItemByNameOrItemByType("name");

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("name",
				"name");
	}

	@Test
	public void testAddItem_Positive() {
		// Given
		ItemDto itemDto = new ItemDto();
		itemDto.setItemId(1L);
		Item item = new Item();
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		// When
		ItemDto result = itemService.addItem(itemDto);

		// Then
		assertNotNull(result);
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	public void testDeleteItem_Positive() {
		// Given
		Item item = new Item();
		item.setItemId(1L);
		item.setItemName("Test Item");
		when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

		// When
		String result = itemService.deleteItem(1L);

		// Then
		assertEquals("Test Item deleted successfully with id 1", result);
		verify(itemRepository, times(1)).deleteById(1L);
	}

	@Test
	public void testDeleteItem_Negative() {
		// Given
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ResourceNotFoundException.class, () -> {
			itemService.deleteItem(1L);
		});
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	public void testUpdateItem_Positive() {
		// Given
		ItemDto itemDto = new ItemDto();
		itemDto.setItemId(1L);
		itemDto.setItemName("Updated Item");
		Item existingItem = new Item();
		existingItem.setItemId(1L);
		when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
		when(itemRepository.save(any(Item.class))).thenReturn(existingItem);

		// When
		ItemDto result = itemService.updateItem(1L, itemDto);

		// Then
		assertNotNull(result);
		assertEquals("Updated Item", result.getItemName());
		verify(itemRepository, times(1)).findById(1L);
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	public void testUpdateItem_Negative_ItemNotFound() {
		// Given
		ItemDto itemDto = new ItemDto();
		when(itemRepository.findById(1L)).thenReturn(Optional.empty());

		// When & Then
		assertThrows(ResourceNotFoundException.class, () -> {
			itemService.updateItem(1L, itemDto);
		});
		verify(itemRepository, times(1)).findById(1L);
	}

	@Test
	public void testSortItemByPrice_Positive() {
		// Given
		Item item1 = new Item();
		item1.setItemPrice(10.0);
		Item item2 = new Item();
		item2.setItemPrice(5.0);
		List<Item> items = Arrays.asList(item1, item2);
		when(itemRepository.findByOrderByItemPrice()).thenReturn(items);

		// When
		List<ItemDto> result = itemService.sortItemByPrice();

		// Then
		assertEquals(2, result.size());
		verify(itemRepository, times(1)).findByOrderByItemPrice();
	}

	@Test
	public void testSortItemByPrice_Negative() {
		// Given
		when(itemRepository.findByOrderByItemPrice()).thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.sortItemByPrice();

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByOrderByItemPrice();
	}

	@Test
	public void testSortItemByName_Positive() {
		// Given
		Item item1 = new Item();
		item1.setItemName("Banana");
		Item item2 = new Item();
		item2.setItemName("Apple");
		List<Item> items = Arrays.asList(item1, item2);
		when(itemRepository.findByOrderByItemName()).thenReturn(items);

		// When
		List<ItemDto> result = itemService.sortItemByName();

		// Then
		assertEquals(2, result.size());
		verify(itemRepository, times(1)).findByOrderByItemName();
	}

	@Test
	public void testSortItemByName_Negative() {
		// Given
		when(itemRepository.findByOrderByItemName()).thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.sortItemByName();

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByOrderByItemName();
	}

	@Test
	public void testAddItem_NullInput() {
		// Given
		ItemDto itemDto = null;

		// When & Then
		assertThrows(IllegalArgumentException.class, () -> {
			itemService.addItem(itemDto);
		});

		verify(itemRepository, times(0)).save(any(Item.class));
	}

	@Test
	public void testAddItem_MissingFields() {
		// Given
		ItemDto itemDto = new ItemDto();
		// Assume itemDto is missing required fields

		Item item = new Item();
		when(itemRepository.save(any(Item.class))).thenReturn(item);

		// When
		ItemDto result = itemService.addItem(itemDto);

		// Then
		assertNotNull(result);
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	public void testUpdateItem_MissingFields() {
		// Given
		ItemDto itemDto = new ItemDto();
		// Assume itemDto is missing required fields

		Item existingItem = new Item();
		existingItem.setItemId(1L);
		when(itemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
		when(itemRepository.save(any(Item.class))).thenReturn(existingItem);

		// When
		ItemDto result = itemService.updateItem(1L, itemDto);

		// Then
		assertNotNull(result);
		verify(itemRepository, times(1)).findById(1L);
		verify(itemRepository, times(1)).save(any(Item.class));
	}

	@Test
	public void testGetItemByNameOrItemByType_EmptyString() {
		// Given
		when(itemRepository.findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("", ""))
				.thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.getItemByNameOrItemByType("");

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase("", "");
	}

	@Test
	public void testGetItemByNameOrItemByType_NullString() {
		// Given
		when(itemRepository.findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase(null, null))
				.thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.getItemByNameOrItemByType(null);

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase(null, null);
	}

	@Test
	public void testSortItemByPrice_NoItems() {
		// Given
		when(itemRepository.findByOrderByItemPrice()).thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.sortItemByPrice();

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByOrderByItemPrice();
	}

	@Test
	public void testSortItemByName_NoItems() {
		// Given
		when(itemRepository.findByOrderByItemName()).thenReturn(Collections.emptyList());

		// When
		List<ItemDto> result = itemService.sortItemByName();

		// Then
		assertTrue(result.isEmpty());
		verify(itemRepository, times(1)).findByOrderByItemName();
	}
}
