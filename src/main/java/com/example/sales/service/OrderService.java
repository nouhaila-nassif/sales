package com.example.sales.service;

import com.example.sales.dto.OrderItemRequest;
import com.example.sales.dto.OrderRequest;
import com.example.sales.model.*;
import com.example.sales.repository.ClientRepository;
import com.example.sales.repository.OrderRepository;
import com.example.sales.repository.ProductRepository;
import com.example.sales.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    public Order createOrder(OrderRequest request, String username) {
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        Order order = new Order();
        order.setSeller(seller);
        order.setClient(client);
        order.setStatus(OrderStatus.CREATED);

        List<OrderItem> items = new ArrayList<>();
        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            items.add(item);
        }
        order.setItems(items);

        return orderRepository.save(order);
    }

    public List<Order> getOrdersBySeller(String username) {
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return orderRepository.findBySeller(seller);
    }

    public Order getOrderByIdAndSeller(Long id, String username) {
        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        return orderRepository.findByIdAndSeller(id, seller)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée ou accès interdit"));
    }

    public Order updateOrder(Long id, OrderRequest request, String username) {
        Order order = getOrderByIdAndSeller(id, username);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Impossible de modifier une commande validée ou livrée");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));

        order.setClient(client);
        order.getItems().clear();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produit non trouvé"));

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            order.getItems().add(item);
        }
        return orderRepository.save(order);
    }

    public void cancelOrder(Long id, String username) {
        Order order = getOrderByIdAndSeller(id, username);
        if (order.getStatus() != OrderStatus.CREATED) {
            throw new RuntimeException("Impossible d'annuler une commande validée ou livrée");
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }
}
