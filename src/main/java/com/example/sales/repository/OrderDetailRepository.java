package com.example.sales.repository;

import com.example.sales.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailRepository extends JpaRepository<OrderItem, Long> {
}
