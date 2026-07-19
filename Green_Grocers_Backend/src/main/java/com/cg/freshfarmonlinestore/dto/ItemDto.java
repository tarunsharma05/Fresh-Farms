package com.cg.freshfarmonlinestore.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
	
	private long itemId;
	
	@NotBlank(message = "Item Name is mandatory")
	private String itemName;
	
	@NotNull(message = "Item Price is mandatory")
    @Positive(message = "Item Price must be positive")
	private double itemPrice;
	
	@NotBlank(message = "Item Type is mandatory")
	private String itemType;
	
	@NotBlank(message = "Item Image URL is mandatory")
	private String imageUrl;
	
	@NotBlank(message = "Item Description is mandatory")
	private String description;
	
	@NotBlank(message = "Item Quantity is mandatory")
	private String itemQuantity;

}