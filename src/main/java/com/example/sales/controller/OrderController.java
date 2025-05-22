package com.example.sales.controller;

import com.example.sales.dto.OrderRequest;
import com.example.sales.model.Order;
import com.example.sales.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // Créer une commande
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest, Principal principal) {
        try {
            Order order = orderService.createOrder(orderRequest, principal.getName());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Lister les commandes du vendeur connecté
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(Principal principal) {
        List<Order> orders = orderService.getOrdersBySeller(principal.getName());
        return ResponseEntity.ok(orders);
    }

    // Détails d'une commande
    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id, Principal principal) {
        try {
            Order order = orderService.getOrderByIdAndSeller(id, principal.getName());
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Modifier une commande (tant qu’elle n’est pas validée ou livrée)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@PathVariable Long id, @RequestBody OrderRequest orderRequest, Principal principal) {
        try {
            Order updatedOrder = orderService.updateOrder(id, orderRequest, principal.getName());
            return ResponseEntity.ok(updatedOrder);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Annuler une commande (si pas validée)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id, Principal principal) {
        try {
            orderService.cancelOrder(id, principal.getName());
            return ResponseEntity.ok("Commande annulée avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
