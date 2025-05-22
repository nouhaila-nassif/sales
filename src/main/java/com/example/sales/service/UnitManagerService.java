package com.example.sales.service;

import com.example.sales.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitManagerService {

    @Autowired
    private RouteRepository routeRepository;



    public void validatePerformance(Long userId) {
        // Logique de validation sur les commandes/visites
    }
}
