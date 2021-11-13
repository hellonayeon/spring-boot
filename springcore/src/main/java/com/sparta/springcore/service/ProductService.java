package com.sparta.springcore.service;

import com.sparta.springcore.domain.Product;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
    }

    @Transactional
    public Product createProduct(ProductRequestDto requestDto, Long userId) throws SQLException {
        Product product = new Product(requestDto, userId);
        productRepository.save(product);
        return product;
    }

    @Transactional
    public Long updateProduct(Long id, ProductMypriceRequestDto requestDto) throws SQLException {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        int myprice = requestDto.getMyprice();

        product.updateMyPrice(myprice);
        return product.getId();
    }
}
