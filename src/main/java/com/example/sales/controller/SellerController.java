package com.example.sales.controller;

import com.example.sales.Mapper.SellerMapper;
import com.example.sales.dto.SellerDTO;
import com.example.sales.model.Seller;
import com.example.sales.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping
    public ResponseEntity<SellerDTO> createSeller(@RequestBody SellerDTO dto) {
        Seller saved = sellerService.createSeller(dto);
        return ResponseEntity.ok(SellerMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<SellerDTO>> getAllSellers() {
        List<SellerDTO> sellers = sellerService.getAllSellers().stream()
                .map(SellerMapper::toDTO)
                .toList();
        return ResponseEntity.ok(sellers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable Long id) {
        return sellerService.getSellerById(id)
                .map(SellerMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}

