package com.example.sales.dto;

import com.example.sales.model.SellerMode;

import java.util.List;

public class SellerDTO {
    private Long id;
    private Long userId;
    private SellerMode sellerMode;
    private List<Long> assignedRouteIds;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SellerMode getSellerMode() {
        return sellerMode;
    }

    public void setSellerMode(SellerMode sellerMode) {
        this.sellerMode = sellerMode;
    }

    public List<Long> getAssignedRouteIds() {
        return assignedRouteIds;
    }

    public void setAssignedRouteIds(List<Long> assignedRouteIds) {
        this.assignedRouteIds = assignedRouteIds;
    }
// Getters & Setters
}
