package com.example.sales.model;

import jakarta.persistence.*;

@Entity
@Table(name = "promotions")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    private double discountPercent;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // Constructors, getters, setters
}
