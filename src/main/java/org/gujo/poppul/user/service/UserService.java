package org.gujo.poppul.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gujo.poppul.user.dto.UserRequest;
import org.gujo.poppul.user.dto.UserResponse;
import org.gujo.poppul.user.entity.User;
import org.gujo.poppul.user.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스.
 * 회원가입 및 Spring Security 인증을 지원합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 사용자 회원가입을 처리합니다.
     *
     * @param userRequest 회원가입 요청 데이터 (ID, 비밀번호, 이름)
     * @return UserResponse 회원가입 결과 응답
     */
    @Transactional
    public UserResponse register(UserRequest userRequest) {
        // 입력 검증
        if (!isValidInput(userRequest)) {
            return UserResponse.builder()
                    .success(false)
                    .message("ID, 비밀번호, 이름은 필수 입력 항목입니다.")
                    .build();
        }

        // ID 중복 체크
        if (userRepository.findById(userRequest.getId()).isPresent()) {
            return UserResponse.builder()
                    .success(false)
                    .message("이미 존재하는 ID입니다.")
                    .build();
        }

        try {
            User user = createUser(userRequest);
            userRepository.save(user);
            log.info("User registered: {}", user.getId());

            return buildSuccessResponse(user);
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage(), e);
            return UserResponse.builder()
                    .success(false)
                    .message("회원가입 중 오류가 발생했습니다.")
                    .build();
        }
    }

    /**
     * Spring Security에서 사용자 인증을 위해 호출됩니다.
     *
     * @param username 사용자 ID
     * @return UserDetails 인증된 사용자 정보
     * @throws UsernameNotFoundException 사용자가 존재하지 않을 경우
     */
    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findById(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getId())
                .password(user.getPassword())
                .roles("USER") // 기본 역할 설정
                .build();
    }

    // 입력 검증 헬퍼 메서드
    private boolean isValidInput(UserRequest userRequest) {
        return userRequest != null &&
                userRequest.getId() != null && !userRequest.getId().trim().isEmpty() &&
                userRequest.getPassword() != null && !userRequest.getPassword().trim().isEmpty() &&
                userRequest.getName() != null && !userRequest.getName().trim().isEmpty();
    }

    // 사용자 엔티티 생성 헬퍼 메서드
    private User createUser(UserRequest userRequest) {
        return User.builder()
                .id(userRequest.getId())
                .name(userRequest.getName())
                .password(passwordEncoder.encode(userRequest.getPassword()))
                .build();
    }

    /**
     * 현재 인증된 사용자의 이름을 반환합니다.
     *
     * @return String 현재 사용자의 이름, 인증되지 않은 경우 null
     */
    @Transactional(readOnly = true)
    public String getCurrentUserName() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            String username = auth.getName();
            return userRepository.findById(username)
                    .map(User::getName)
                    .orElse(null);
        }
        return null;
    }

    // 성공 응답 생성 헬퍼 메서드
    private UserResponse buildSuccessResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .success(true)
                .message("회원가입 성공")
                .build();
    }
}