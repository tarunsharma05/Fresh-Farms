package com.cg.freshfarmonlinestore.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.cg.freshfarmonlinestore.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{

    // Find items by name or type, ignoring case
    public List<Item> findByItemNameContainingIgnoreCaseOrItemTypeContainingIgnoreCase(String itemName, String itemType);

    // Find items ordered by price
    public List<Item> findByOrderByItemPrice();

    // Find items ordered by name
    public List<Item> findByOrderByItemName();
}
