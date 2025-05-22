package com.example.sales.service;

import com.example.sales.model.Route;
import com.example.sales.repository.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public List<Route> findAll() {
        return routeRepository.findAll();
    }
    public Route createRoute(Route route) {
        return routeRepository.save(route); // Hibernate gère insert automatiquement
    }
    public Optional<Route> findById(Long id) {
        return routeRepository.findById(id);
    }

    public Route save(Route route) {
        return routeRepository.save(route);
    }

    public void deleteById(Long id) {
        routeRepository.deleteById(id);
    }
}
