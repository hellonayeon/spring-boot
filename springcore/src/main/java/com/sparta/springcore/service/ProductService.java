package com.sparta.springcore.service;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.domain.Product;
import com.sparta.springcore.domain.User;
import com.sparta.springcore.dto.ItemDto;
import com.sparta.springcore.dto.ProductMypriceRequestDto;
import com.sparta.springcore.dto.ProductRequestDto;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final FolderRepository folderRepository;

    public List<Product> getAllProducts() { return productRepository.findAll(); }

    public Page<Product> getProducts(Long userId, int page, int size, String sortBy, boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return productRepository.findAllByUserId(userId, pageable);
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

    @Transactional
    public Product addFolder(Long productId, Long folderId, User user) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new NullPointerException("해당 관심상품 아이디가 존재하지 않습니다.")
        );

        Folder folder = folderRepository.findById(folderId).orElseThrow(
                () -> new NullPointerException("해당 폴더 아이디가 존재하지 않습니다.")
        );

        // 이미 동일한 폴더에 추가된 관심상품일 경우
        Optional<Product> sameProduct = productRepository.findByIdAndFolderList_Id(productId, folderId);
        if(sameProduct.isPresent()) {
            throw new IllegalArgumentException("폴더에 이미 해당 상품이 존재합니다.");
        }

        Long userId = user.getId();
        Long productUserId = product.getUserId();
        Long folderUserId = folder.getUser().getId();

        if(!userId.equals(productUserId) || !userId.equals(folderUserId)) {
            throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 폴더가 아니어서 추가하지 못했습니다.");
        }

        product.addFolder(folder);
        return product;
    }

    @Transactional
    public Long updateBySearch(Long id, ItemDto itemDto) {
        Product product = productRepository.findById(id).orElseThrow(
                () -> new NullPointerException("해당 아이디가 존재하지 않습니다.")
        );
        product.updateByItemDto(itemDto);
        return id;
    }
}
