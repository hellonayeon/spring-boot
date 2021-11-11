package com.sparta.itembasket.dto;

import lombok.Getter;
import java.util.List;

@Getter
public class FolderCreateRequestDto {
    List<String> folderNames;
}