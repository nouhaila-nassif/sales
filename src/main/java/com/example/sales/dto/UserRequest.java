package com.example.sales.dto;

import java.util.Set;

public class UserRequest {
    private String username;
    private String password;
    private Set<String> roles; // ex : ["ROLE_ADMIN", "ROLE_PRE_SELLER"]

    // Getters & setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<String> getRoles() { return roles; }
    public void setRoles(Set<String> roles) { this.roles = roles; }
}
