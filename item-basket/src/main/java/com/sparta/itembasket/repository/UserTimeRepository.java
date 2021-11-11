package com.sparta.itembasket.repository;

import com.sparta.itembasket.domain.User;
import com.sparta.itembasket.domain.UserTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTimeRepository extends JpaRepository<UserTime, Long> {
    UserTime findByUser(User user);
}
