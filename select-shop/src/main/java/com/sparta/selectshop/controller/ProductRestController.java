package com.sparta.selectshop.controller;

import com.sparta.selectshop.models.Product;
import com.sparta.selectshop.models.ProductRepository;
import com.sparta.selectshop.models.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController // JSON 으로 응답하는 자동 응답기
public class ProductRestController {

    private final ProductRepository productRepository;

    @GetMapping("/api/products")
    public List<Product> readProducts() {
        return productRepository.findAll();
    }

    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) {
        Product product = new Product(requestDto);
        return productRepository.save(product);
    }
}
