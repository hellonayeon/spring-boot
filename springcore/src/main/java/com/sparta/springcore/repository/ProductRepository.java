package com.sparta.springcore.repository;

import com.sparta.springcore.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    //
    Page<Product> findAllByUserIdAndFolderList_Id(Long userId, Long folderId, Pageable pageable);
    Optional<Product> findByIdAndFolderList_Id(Long productId, Long folderId);
}
