package com.sparta.springcore.controller;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.dto.FolderCreateRequestDto;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
