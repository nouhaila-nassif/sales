package com.example.sales.config;

import com.example.sales.repository.RoleRepository;
import com.example.sales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
//
//
//    @PostConstruct
//    public void init() {
//        // Chercher par RoleName enum
//        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
//                .orElseGet(() -> roleRepository.save(new Role(RoleName.ADMIN)));
//
//        if (userRepository.findByUsername("admin").isEmpty()) {
//            User admin = new User();
//            admin.setUsername("admin");
//            admin.setPassword(passwordEncoder.encode("admin123"));
//            admin.setRoles(Set.of(adminRole));
//            userRepository.save(admin);
//        }
//    }
}
