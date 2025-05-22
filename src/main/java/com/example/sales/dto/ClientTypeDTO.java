package com.example.sales.dto;

public class ClientTypeDTO {

    private Long id;
    private String name;

    // Constructeurs
    public ClientTypeDTO() {}

    public ClientTypeDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
