package com.sparta.springcore.controller;

import com.sparta.springcore.domain.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /* 전체 상품 조회 */
    @GetMapping("/api/products")
    public List<Product> getProducts() throws SQLException {
        return productService.getProducts();
    }

    /* 상품 등록 */
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto) throws SQLException {
        return productService.createProduct(requestDto);
    }

    /* 상품 최저가 변경 */
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        return productService.updateProduct(id, requestDto);
    }


}
