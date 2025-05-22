package com.example.sales.service;

import com.example.sales.model.Promotion;
import com.example.sales.repository.ProductRepository;
import com.example.sales.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private ProductRepository productRepository;

    public Promotion createPromotion(Promotion promo) {
        return promotionRepository.save(promo);
    }

    public List<Promotion> getPromotionsByProduct(Long productId) {
        return promotionRepository.findByProductId(productId);
    }


}

