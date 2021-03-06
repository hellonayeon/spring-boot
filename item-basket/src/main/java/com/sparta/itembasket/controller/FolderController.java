package com.sparta.itembasket.controller;

import com.sparta.itembasket.domain.Folder;
import com.sparta.itembasket.domain.Product;
import com.sparta.itembasket.dto.FolderCreateRequestDto;
import com.sparta.itembasket.exception.ApiException;
import com.sparta.itembasket.service.FolderService;
import com.sparta.itembasket.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class FolderController {
    // 멤버 변수 선언
    private final FolderService folderService;

    // 회원이 등록한 모든 폴더 조회
    @GetMapping("/api/folders")
    public List<Folder> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return folderService.getFolders(userDetails.getUser());
    }

    // 회원이 폴더 추가
    @PostMapping("/api/folders")
    public List<Folder> addFolders(@RequestBody FolderCreateRequestDto folderCreateRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        List<String> folderNames = folderCreateRequestDto.getFolderNames();
        return folderService.createFolders(folderNames, userDetails.getUser());
    }

    // 회원이 등록한 폴더 내 모든 상품 조회
    @GetMapping("/api/folders/{folderId}/products")
    public Page<Product> getProductsOnFolder(@PathVariable("folderId") Long folderId,
                                             @RequestParam("page") int page,
                                             @RequestParam("size") int size,
                                             @RequestParam("sortBy") String sortBy,
                                             @RequestParam("isAsc") boolean isAsc,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        page = page - 1;
        return folderService.getProductsOnFolder(userDetails.getUser(), page, size, sortBy, isAsc, folderId);
    }

    // 예외 처리
    // Service 단에서 생성한 new IllegalArgumentException() 넘어오면 동작
    @ExceptionHandler({ IllegalArgumentException.class })
    public ResponseEntity<Object> handle(IllegalArgumentException ex) {
        ApiException apiException = new ApiException(
                ex.getMessage(),
                // HTTP 400 -> Client Error
                HttpStatus.BAD_REQUEST
        );

        return new ResponseEntity<>(
                apiException,
                HttpStatus.BAD_REQUEST
        );
    }
}