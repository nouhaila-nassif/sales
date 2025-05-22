package com.example.sales.service;

import com.example.sales.Mapper.SellerMapper;
import com.example.sales.dto.SellerDTO;
import com.example.sales.model.Route;
import com.example.sales.model.Seller;
import com.example.sales.model.User;
import com.example.sales.repository.RouteRepository;
import com.example.sales.repository.SellerRepository;
import com.example.sales.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerService {

    @Autowired
    private SellerRepository sellerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RouteRepository routeRepository;

    public Seller createSeller(SellerDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<Route> routes = routeRepository.findAllById(dto.getAssignedRouteIds());

        Seller seller = SellerMapper.toEntity(dto, user, routes);
        return sellerRepository.save(seller);
    }

    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    public Optional<Seller> getSellerById(Long id) {
        return sellerRepository.findById(id);
    }

    // Ajoute update/delete si besoin
}
