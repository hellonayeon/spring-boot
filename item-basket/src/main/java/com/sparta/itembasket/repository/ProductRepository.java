package com.sparta.itembasket.repository;

import com.sparta.itembasket.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}