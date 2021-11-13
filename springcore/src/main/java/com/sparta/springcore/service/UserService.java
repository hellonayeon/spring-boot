package com.sparta.springcore.service;

import com.sparta.springcore.domain.User;
import com.sparta.springcore.domain.UserRole;
import com.sparta.springcore.dto.SignupRequestDto;
import com.sparta.springcore.repository.UserRepository;
import com.sparta.springcore.security.UserDetailsImpl;
import com.sparta.springcore.security.kakao.KakaoOAuth2;
import com.sparta.springcore.security.kakao.KakaoUserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOAuth2 kakaoOAuth2;
    private final AuthenticationManager authenticationManager;
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

    public void kakaoLogin(String authorizedCode) {
        // Kakao OAuth2를 통해 카카오 사용자 정보 조회
        KakaoUserInfo kakaoUserInfo = kakaoOAuth2.getUserInfo(authorizedCode);
        Long kakaoId = kakaoUserInfo.getId();
        String nickname = kakaoUserInfo.getNickname();
        String email = kakaoUserInfo.getEmail();

        // 중복된 카카오 사용자가 있을 경우 트랜잭션 X
        User kakaoUser = userRepository.findByKakaoId(kakaoId).orElse(null);

        if(kakaoUser == null) {
            // 카카오 이메일 == 회원가입 이메일
            User sameEmailUser = userRepository.findByEmail(email).orElse(null);
            // 카카오 아이디가 있는데 별도로 회원가입을 한 경우
            // 기존 사용자 정보에 '카카오 아이디 추가'
            if(sameEmailUser != null) {
                kakaoUser = sameEmailUser;
                kakaoUser.setKakaoId(kakaoId);
                userRepository.save(kakaoUser);
            }
            else {
                String username = nickname; // user nickname(sub id) = kakao nickname
                String password = kakaoId + ADMIN_TOKEN; // user password = kakao id + secret token string

                String encodedPassword = passwordEncoder.encode(password);
                UserRole userRole = UserRole.USER;

                kakaoUser = new User(kakaoId, username, encodedPassword, email, userRole);
                userRepository.save(kakaoUser);
            }
        }

        // 로그인 처리
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
