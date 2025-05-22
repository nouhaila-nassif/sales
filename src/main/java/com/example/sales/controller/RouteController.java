package com.example.sales.controller;

import com.example.sales.model.Route;
import com.example.sales.service.RouteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    // Récupérer toutes les routes
    @GetMapping
    public List<Route> getAllRoutes() {
        return routeService.findAll();
    }

    // Récupérer une route par ID
    @GetMapping("/{id}")
    public ResponseEntity<Route> getRouteById(@PathVariable Long id) {
        return routeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Route> createRoute(@RequestBody Route route) {
        Route savedRoute = routeService.createRoute(route);
        return ResponseEntity.ok(savedRoute);
    }

    // Mettre à jour une route existante
    @PutMapping("/{id}")
    public ResponseEntity<Route> updateRoute(@PathVariable Long id, @RequestBody Route updatedRoute) {
        return routeService.findById(id)
                .map(route -> {
                    route.setName(updatedRoute.getName());
                    // ici tu peux aussi gérer les sellers si besoin
                    return ResponseEntity.ok(routeService.save(route));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // Supprimer une route
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        if (routeService.findById(id).isPresent()) {
            routeService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
