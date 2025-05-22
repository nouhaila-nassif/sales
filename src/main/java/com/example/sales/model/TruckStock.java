package com.example.sales.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Entity
public class TruckStock {
    @Id @GeneratedValue
    private Long id;
    private int quantity;

    @ManyToOne(optional = false)
    private Seller seller;

    @ManyToOne(optional = false)
    private Product product;

    // Getters & Setters
}