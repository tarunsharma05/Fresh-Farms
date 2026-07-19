package com.cg.freshfarmonlinestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cg.freshfarmonlinestore.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
