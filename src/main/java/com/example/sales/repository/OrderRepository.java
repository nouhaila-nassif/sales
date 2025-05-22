package com.example.sales.repository;

import com.example.sales.model.Order;
import com.example.sales.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findBySeller(User seller);

    Optional<Order> findByIdAndSeller(Long id, User seller); // ✅ AJOUTER CETTE LIGNE
}
