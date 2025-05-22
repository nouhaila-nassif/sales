package com.example.sales.Mapper;

import com.example.sales.dto.SellerDTO;
import com.example.sales.model.Route;
import com.example.sales.model.Seller;
import com.example.sales.model.User;

import java.util.List;

public class SellerMapper {

    public static Seller toEntity(SellerDTO dto, User user, List<Route> routes) {
        Seller seller = new Seller();
        seller.setUser(user);
        seller.setSellerMode(dto.getSellerMode());
        seller.setAssignedRoutes(routes);
        return seller;
    }

    public static SellerDTO toDTO(Seller seller) {
        SellerDTO dto = new SellerDTO();
        dto.setId(seller.getId());
        dto.setUserId(seller.getUser().getId());
        dto.setSellerMode(seller.getSellerMode());
        dto.setAssignedRouteIds(seller.getAssignedRoutes().stream()
                .map(Route::getId)
                .toList());
        return dto;
    }
}
