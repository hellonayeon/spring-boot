package com.sparta.itembasket.repository;

import com.sparta.itembasket.domain.Folder;
import com.sparta.itembasket.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    Folder findByName(String folderName);
}