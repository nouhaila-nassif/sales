package com.example.sales.controller;

import com.example.sales.dto.JwtResponse;
import com.example.sales.dto.LoginRequest;
import com.example.sales.dto.SignupRequest;
import com.example.sales.model.Role;
import com.example.sales.model.RoleName;
import com.example.sales.model.User;
import com.example.sales.repository.RoleRepository;
import com.example.sales.repository.UserRepository;
import com.example.sales.security.JwtUtils;
import com.example.sales.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body("Erreur: Nom d'utilisateur déjà utilisé!");
        }

        // Créer un nouvel utilisateur
        User user = new User(signUpRequest.getUsername(), passwordEncoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(RoleName.PRE_SELLER)
                    .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toUpperCase()) {
                    case "ADMIN":
                        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
                        roles.add(adminRole);
                        break;
                    case "SUPERVISOR":
                        Role supervisorRole = roleRepository.findByName(RoleName.SUPERVISOR)
                                .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
                        roles.add(supervisorRole);
                        break;
                    case "DIRECT_SELLER":
                        Role directSellerRole = roleRepository.findByName(RoleName.DIRECT_SELLER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
                        roles.add(directSellerRole);
                        break;
                    case "UNIT_MANAGER":
                        Role unitManagerRole = roleRepository.findByName(RoleName.UNIT_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
                        roles.add(unitManagerRole);
                        break;
                    default:
                        Role defaultRole = roleRepository.findByName(RoleName.PRE_SELLER)
                                .orElseThrow(() -> new RuntimeException("Erreur: Rôle non trouvé."));
                        roles.add(defaultRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("Utilisateur enregistré avec succès!");
    }



    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Récupérer l'utilisateur authentifié pour extraire ses rôles
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Erreur: Utilisateur non trouvé."));

        String jwt = jwtUtils.generateJwt(user.getUsername(), user.getRoles());

        return ResponseEntity.ok(new JwtResponse(jwt));
    }



}