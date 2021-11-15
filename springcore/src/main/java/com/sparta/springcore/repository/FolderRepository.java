package com.sparta.springcore.repository;

import com.sparta.springcore.domain.Folder;
import com.sparta.springcore.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    List<Folder> findAllByUser(User user);
    Optional<Folder> findByNameAndUserId(String folderName, Long userId);
}
