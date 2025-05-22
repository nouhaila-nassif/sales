package com.example.sales.repository;

import com.example.sales.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    // Tu peux ajouter ici des méthodes personnalisées si besoin, par exemple :
    // boolean existsByUserId(Long userId);
}
