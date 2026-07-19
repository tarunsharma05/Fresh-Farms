
package com.cg.freshfarmonlinestore.entity;
 
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "cart")
public class Cart {
 

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private long cartId;
 
    @Column(name = "entry_date")
    private LocalDate entryDate;
 
    @Column(name = "item_count")
    private int itemCount;


    @Column(name = "total_amount")
    private double totalAmount;
 

    @OneToOne(mappedBy = "cart", orphanRemoval = true)
    @JsonIgnore
    private Customer customer;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "carts_items",
            joinColumns = @JoinColumn(name = "carts_id", referencedColumnName = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "items_id", referencedColumnName = "item_id"))
    private List<Item> items;

}
