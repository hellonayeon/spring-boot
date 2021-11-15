package com.sparta.springcore.service;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.domain.Product;
import com.sparta.springcore.domain.User;
import com.sparta.springcore.repository.FolderRepository;
import com.sparta.springcore.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FolderService {
    private final FolderRepository folderRepository;
    private final ProductRepository productRepository;

    public List<Folder> getFolders(User user) {
        return folderRepository.findAllByUser(user);
    }

    public List<Folder> createFolders(List<String> folderNameList, User user) {
        List<Folder> folderList = new ArrayList<>();
        for(String folderName : folderNameList) {
            Folder folder = new Folder(folderName, user);
            folderList.add(folder);
        }
        folderList = folderRepository.saveAll(folderList);
        return folderList;
    }

    public Page<Product> getProductsOnFolder(User user, int page, int size, String sortBy, boolean isAsc, Long folderId) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAllByUserIdAndFolderList_Id(user.getId(), folderId, pageable);
    }
}
