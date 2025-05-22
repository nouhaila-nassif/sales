package com.example.sales.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")

@Entity
@Table(name = "users")
public class User {

    // Getters & Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    // Constructeurs
    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    // Getter username
    public String getUsername() {
        return username;
    }

    // Setter username
    public void setUsername(String username) {
        this.username = username;
    }

    // Getter password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}


// getters/setters

