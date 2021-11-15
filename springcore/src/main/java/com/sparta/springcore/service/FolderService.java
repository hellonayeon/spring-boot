package com.sparta.springcore.service;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.domain.Product;
import com.sparta.springcore.domain.User;
import com.sparta.springcore.exception.ApiRequestException;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    @Transactional(readOnly = false)
    public List<Folder> createFolders(List<String> folderNameList, User user) {

        List<Folder> folderList = new ArrayList<>();

        for(String folderName : folderNameList) {
            Optional<Folder> folderInDB = folderRepository.findByNameAndUserId(folderName, user.getId());
            if (folderInDB.isPresent()) {
                throw new ApiRequestException("중복된 폴더명 (" + folderName +") 을 삭제하고 재시도해 주세요!");
            }

            Folder folder = new Folder(folderName, user);
            folder = folderRepository.save(folder);
            folderList.add(folder);
        }
        return folderList;
    }

    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
}
