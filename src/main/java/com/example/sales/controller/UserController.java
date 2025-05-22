package com.example.sales.controller;

import com.example.sales.dto.UserRequest;
import com.example.sales.model.Role;
import com.example.sales.model.RoleName;
import com.example.sales.model.User;
import com.example.sales.repository.RoleRepository;
import com.example.sales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> listUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        if (userRepository.findByUsername(userRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Erreur: Username déjà utilisé!");
        }

        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (userRequest.getRoles() == null || userRequest.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.PRE_SELLER)
                    .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
            roles.add(userRole);
        } else {
            roles = userRequest.getRoles().stream()
                    .map(roleNameStr -> {
                        RoleName roleName = RoleName.valueOf(roleNameStr);
                        return roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
                    }).collect(Collectors.toSet());
        }
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur créé avec succès!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        user.setUsername(userRequest.getUsername());
        if (userRequest.getPassword() != null && !userRequest.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }

        if (userRequest.getRoles() != null && !userRequest.getRoles().isEmpty()) {
            Set<Role> roles = userRequest.getRoles().stream()
                    .map(roleNameStr -> {
                        RoleName roleName = RoleName.valueOf(roleNameStr);
                        return roleRepository.findByName(roleName)
                                .orElseThrow(() -> new RuntimeException("Erreur: Role non trouvé."));
                    }).collect(Collectors.toSet());
            user.setRoles(roles);
        }

        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur modifié avec succès!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès!");
    }
}
