package com.example.sales.config;

import com.example.sales.security.JwtAuthFilter;
import com.example.sales.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthFilter jwtAuthFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Endpoints d'authentification ouverts à tous
                        .requestMatchers("/api/auth/**", "/api/sellers").permitAll()

                        // Seul l'admin peut accéder à toutes ces ressources
                        .requestMatchers(
                                "/api/products/**",
                                "/api/clients/**",
                                "/api/routes/**",
                                "/api/users/**"
                        ).hasRole("ADMIN")
                        // Voir ses routes et clients assignés (GET)
                        .requestMatchers(HttpMethod.GET, "/api/preseller/routes").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/preseller/routes/").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/preseller/clients").hasRole("PRE_SELLER")

                        // CRUD commandes (POST, PUT, DELETE, GET)
                        .requestMatchers(HttpMethod.POST, "/api/preseller/orders").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/preseller/orders/**").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/api/preseller/orders/**").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/preseller/orders").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/preseller/orders/**").hasRole("PRE_SELLER")

                        // Suivi de revisite (GET, POST)
                        .requestMatchers(HttpMethod.GET, "/api/preseller/revisits/today").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/preseller/clients/").hasRole("PRE_SELLER")
                        .requestMatchers(HttpMethod.POST, "/api/preseller/clients/").hasRole("PRE_SELLER")

                        // Charger le stock quotidiennement (POST ou PUT selon API)
                        .requestMatchers(HttpMethod.POST, "/api/directseller/stock/load").hasRole("DIRECT_SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/directseller/stock/load").hasRole("DIRECT_SELLER")

                        // Vente en temps réel : commandes, clients, produits, promotions
                        .requestMatchers(HttpMethod.GET, "/api/directseller/clients").hasRole("DIRECT_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/directseller/products").hasRole("DIRECT_SELLER")
                        .requestMatchers(HttpMethod.GET, "/api/directseller/promotions").hasRole("DIRECT_SELLER")

                        // Création & modification commandes (vente en temps réel)
                        .requestMatchers(HttpMethod.POST, "/api/directseller/orders").hasRole("DIRECT_SELLER")
                        .requestMatchers(HttpMethod.PUT, "/api/directseller/orders/**").hasRole("DIRECT_SELLER")
                        .requestMatchers(HttpMethod.DELETE, "/api/directseller/orders/**").hasRole("DIRECT_SELLER")

                        // Synchronisation avec le système central (endpoint dédié, exemple)
                        .requestMatchers(HttpMethod.POST, "/api/directseller/sync").hasRole("DIRECT_SELLER")

                                // Supervisor : gestion des vendeurs, approbations, contrôle tournées
                        // Suivi de performance des vendeurs
                        .requestMatchers(HttpMethod.GET, "/api/supervisor/sellers/performance").hasRole("SUPERVISOR")

                        // Approbatation des promotions clients ou grosses commandes
                        .requestMatchers(HttpMethod.POST, "/api/supervisor/promotions/approve/**").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.POST, "/api/supervisor/orders/approve/**").hasRole("SUPERVISOR")

                        // Contrôle du respect des tournées (SMM/HFS revisites)
                        .requestMatchers(HttpMethod.GET, "/api/supervisor/routes/check").hasRole("SUPERVISOR")
                        .requestMatchers(HttpMethod.GET, "/api/supervisor/revisits/**").hasRole("SUPERVISOR")

                        // Check-in quotidien avec routes assignées
                        .requestMatchers(HttpMethod.POST, "/api/supervisor/routes/checkin").hasRole("SUPERVISOR")

                                // Unit Manager
                        // Valider les allocations de stock
                        .requestMatchers(HttpMethod.POST, "/api/unit-manager/stock/allocations/validate").hasRole("UNIT_MANAGER")

                        // Accès aux rapports multi-routes
                        .requestMatchers(HttpMethod.GET, "/api/unit-manager/reports/multi-routes/**").hasRole("UNIT_MANAGER")

                        // Suivi de performance des vendeurs
                        .requestMatchers(HttpMethod.GET, "/api/unit-manager/sellers/performance/**").hasRole("UNIT_MANAGER")

                        // Contrôle budget & efficacité des promotions
                        .requestMatchers(HttpMethod.GET, "/api/unit-manager/promotions/budget/**").hasRole("UNIT_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/unit-manager/promotions/effectiveness/**").hasRole("UNIT_MANAGER")

                        // Gestion des demandes administratives (utilisateurs, anomalies)
                        .requestMatchers(HttpMethod.POST, "/api/unit-manager/admin/requests/**").hasRole("UNIT_MANAGER")
                        .requestMatchers(HttpMethod.GET, "/api/unit-manager/admin/requests/**").hasRole("UNIT_MANAGER")


                        // Toute autre requête doit être authentifiée
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
