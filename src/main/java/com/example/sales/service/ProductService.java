package com.example.sales.service;

import com.example.sales.model.Product;
import com.example.sales.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product){
        return productRepository.save(product);
    }

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id){
        return productRepository.findById(id);
    }

    public void deleteProduct(Long id){
        productRepository.deleteById(id);
    }
}
