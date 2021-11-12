package com.sparta.springcore.service;

import com.sparta.springcore.domain.User;
import com.sparta.springcore.domain.UserRole;
import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    public User registerUser(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();

        Optional<User> found = userRepository.findByUsername(username);
        if(found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID가 존재합니다.");
        }

        // 비밀번호 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

        UserRole role = UserRole.USER;
        if(requestDto.isAdmin()) {
            if(!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRole.ADMIN;
        }

        User user = new User(username, password, email, role);
        userRepository.save(user);

        return user;
    }
}
