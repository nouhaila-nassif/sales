package com.example.sales.service;

import com.example.sales.repository.ProductRepository;
import com.example.sales.repository.TruckStockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TruckStockService {

    @Autowired
    private TruckStockRepository truckStockRepository;
    @Autowired
    private ProductRepository productRepository;








}
