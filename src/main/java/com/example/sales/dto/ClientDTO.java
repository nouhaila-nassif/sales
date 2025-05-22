package com.example.sales.dto;

public class ClientDTO {
    private Long id;
    private String name;
    private String clientTypeName; // à la place de clientTypeId
    // + getters/setters
    private Long routeId;      // juste l'ID de la Route

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientTypeName() {
        return clientTypeName;
    }

    public void setClientTypeName(String clientTypeName) {
        this.clientTypeName = clientTypeName;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }


    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }
}
