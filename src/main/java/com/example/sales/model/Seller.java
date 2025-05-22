package com.example.sales.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
public class Seller {
    @Id @GeneratedValue
    private Long id;

    @OneToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SellerMode sellerMode;

    @ManyToMany
    @JoinTable(name = "seller_routes",
            joinColumns = @JoinColumn(name = "seller_id"),
            inverseJoinColumns = @JoinColumn(name = "route_id"))
    private List<Route> assignedRoutes;

    // Getters & Setters

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public List<Route> getAssignedRoutes() {
        return assignedRoutes;
    }

    public void setAssignedRoutes(List<Route> assignedRoutes) {
        this.assignedRoutes = assignedRoutes;
    }
}

