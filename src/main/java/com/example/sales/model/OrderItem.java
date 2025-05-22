package com.example.sales.model;

import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    private Double unitPrice;

    private Boolean promotionApplied;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    // Constructeur par défaut
    public OrderItem() {
    }

    // Constructeur avec paramètres (facultatif)
    public OrderItem(Product product, Integer quantity, Double unitPrice, Boolean promotionApplied, Order order) {
        this.product = product;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.promotionApplied = promotionApplied;
        this.order = order;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Boolean getPromotionApplied() {
        return promotionApplied;
    }

    public void setPromotionApplied(Boolean promotionApplied) {
        this.promotionApplied = promotionApplied;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
