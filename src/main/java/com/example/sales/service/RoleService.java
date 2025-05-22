package com.example.sales.service;

import com.example.sales.model.Role;
import com.example.sales.model.RoleName;
import com.example.sales.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createRole(RoleName roleName) {
        Role role = new Role();
        role.setName(roleName);
        return roleRepository.save(role);
    }

    public Set<Role> findByNameIn(Set<String> roleNames) {
        return roleRepository.findByNameIn(roleNames);
    }

    // Autres méthodes selon besoin
}
