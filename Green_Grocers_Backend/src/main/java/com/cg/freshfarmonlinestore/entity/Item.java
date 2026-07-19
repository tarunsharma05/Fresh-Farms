package com.cg.freshfarmonlinestore.entity;
 
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonIgnore;
 
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "item")
public class Item {
 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_id")
	private long itemId;
 
	@Column(name = "item_name", nullable = false)
	private String itemName;
 
	@Column(name = "item_price", nullable = false)
	private double itemPrice;
 
	@Column (name ="image_url")
	private String imageUrl;
	
	@Column(name = "item_description")
	private String description;
	
	@Column(name = "item_quantity")
	private String itemQuantity;
	
	@Column(name = "item_type")
	private String itemType;
	
	@ManyToMany(mappedBy = "items")
	@JsonIgnore
	private List<Cart> carts;

 
}