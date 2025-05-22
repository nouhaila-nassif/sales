package com.example.sales.config;

import com.example.sales.model.Role;
import com.example.sales.model.RoleName;
import com.example.sales.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (RoleName roleName : RoleName.values()) {
            // Vérifier si le rôle existe déjà pour éviter les doublons
            if (roleRepository.findByName(roleName).isEmpty()) {
                Role role = new Role(roleName);
                roleRepository.save(role);
            }
        }
    }
}
