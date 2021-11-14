package com.sparta.springcore.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderCreateRequestDto {
    private List<String> folderNames;
}
