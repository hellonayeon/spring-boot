package com.sparta.springcore.controller;

import com.sparta.springcore.domain.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /* 전체 상품 조회: 관리자용 */
    @Secured("ROLE_ADMIN") // '인가' 필요
    @GetMapping("/api/admin/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /* 사용자별 상품 조회 */
    @GetMapping("/api/products")
    public List<Product> getProducts(@AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        return productService.getProducts(userDetails.getUser().getId());
    }

    /* 상품 등록 */
    @PostMapping("/api/products")
    public Product createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws SQLException {
        return productService.createProduct(requestDto, userDetails.getUser().getId());
    }

    /* 상품 최저가 변경 */
    @PutMapping("/api/products/{id}")
    public Long updateProduct(@PathVariable Long id, @RequestBody ProductMypriceRequestDto requestDto) throws SQLException {
        return productService.updateProduct(id, requestDto);
    }


}
