package com.example.sales.repository;

import com.example.sales.model.ClientType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientTypeRepository extends JpaRepository<ClientType, Long> {
    boolean existsByName(String name);

    Optional<ClientType> findByName(String name);

}
