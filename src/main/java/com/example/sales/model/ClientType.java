package com.example.sales.model;

import jakarta.persistence.*;

@Entity
@Table(name = "client_type")
public class ClientType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


      // Stocké en base sous forme de String ("PRE_SELLER", "DIRECT_SELLER")

    // Constructeurs
    public ClientType() {}



    public void setId(Long id) {
        this.id = id;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }




}
