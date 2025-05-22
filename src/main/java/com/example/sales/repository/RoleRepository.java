package com.example.sales.repository;

import com.example.sales.model.Role;
import com.example.sales.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
    Set<Role> findByNameIn(Set<String> names);
}
