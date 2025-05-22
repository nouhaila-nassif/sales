package com.example.sales.controller;

import com.example.sales.model.Product;
import com.example.sales.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Tout le monde connecté peut voir la liste des produits
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Créer un produit (seulement admin et unit manager)

    @PreAuthorize("hasRole('ADMIN') or hasRole('UNIT_MANAGER')")
    @PostMapping("/create")
    public Product createProduct(@RequestBody Product product) {
        return productRepository.save(product);
    }

    // Modifier un produit
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('UNIT_MANAGER')")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return productRepository.findById(id).map(product -> {
            product.setName(productDetails.getName());
            product.setPrice(productDetails.getPrice());
            // autres champs...
            return ResponseEntity.ok(productRepository.save(product));
        }).orElse(ResponseEntity.notFound().build());
    }

    // Supprimer un produit
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        return productRepository.findById(id).map(product -> {
            productRepository.delete(product);
            return ResponseEntity.<Void>noContent().build(); // ✅ cast explicite
        }).orElse(ResponseEntity.notFound().build());
    }

}
