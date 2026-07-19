package com.cg.freshfarmonlinestore.dto;
 
import java.time.LocalDate;
import java.util.List;
 
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
 

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
 
    private long cartId;
 
    @NotNull(message = "Entry date cannot be null")
    private LocalDate entryDate;
 
    @Min(value = 0, message = "Item count cannot be negative")
    private int itemCount;
    
    @Min(value = 0, message = "Total amount cannot be negative")
    private double totalAmount;
 
    private List<ItemDto> items;
}