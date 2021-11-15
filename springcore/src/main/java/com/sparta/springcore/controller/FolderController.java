package com.sparta.springcore.controller;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.domain.Product;
import com.sparta.springcore.dto.FolderCreateRequestDto;
import com.sparta.springcore.exception.ApiException;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FolderController {
    private final FolderService folderService;

    /* 모든 폴더 조회 */
    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }

    /* 폴더 추가 */
    @PostMapping("/api/folders")
    public List<Folder> createFolders(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody FolderCreateRequestDto requestDto) {
        List<String> folderNames = requestDto.getFolderNames();
        return folderService.createFolders(folderNames, userDetails.getUser());
    }

    /* 폴더별 상품 내용 조회 */
    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsOnFolder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                             @PathVariable("folderId") Long folderId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy") String sortBy,
                                             @RequestParam("isAsc") boolean isAsc) {
        return folderService.getProductsOnFolder(userDetails.getUser(), page-1, size, sortBy, isAsc, folderId);
    }
}
