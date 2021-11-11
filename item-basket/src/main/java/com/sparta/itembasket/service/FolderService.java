package com.sparta.itembasket.service;

import com.sparta.itembasket.domain.Folder;
import com.sparta.itembasket.domain.Product;
import com.sparta.itembasket.domain.User;
import com.sparta.itembasket.exception.ApiRequestException;
import com.sparta.itembasket.repository.FolderRepository;
import com.sparta.itembasket.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class FolderService {
    // 멤버 변수 선언
    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    // 회원 ID 로 등록된 모든 폴더 조회
    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = false)
    public List<Folder> createFolders(List<String> folderNameList, User user) {

        List<Folder> folderList = new ArrayList<>();

        for (String folderName : folderNameList) {
            // 1) DB 에 폴더명이 folderName 인 폴더가 존재하는지?
            Folder folderInDB = folderRepository.findByName(folderName);
            if (folderInDB != null) {
                // DB 에 중복 폴더명 존재한다면 Exception 발생시킴
                throw new ApiRequestException("중복된 폴더명 ('" + folderName + "') 을 삭제하고 재시도해 주세요!");
            }

            // 2) 폴더를 DB 에 저장
            Folder folder = new Folder(folderName, user);
            folder = folderRepository.save(folder);

            // 3) folderList 에 folder Entity 객체를 추가
            folderList.add(folder);
        }

        return folderList;
    }

    // 회원 ID 가 소유한 폴더에 저장되어 있는 상품들 조회
    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
}