package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.dto.LoginRequest;
import org.scoula.dto.LoginResponse;
import org.scoula.dto.UserResponse;
import org.scoula.security.account.CustomUserDetails;
import org.scoula.security.jwt.JwtProvider;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

// 로그인 화면, JWT 발급, 로그인 사용자 조회 요청을 처리하는 컨트롤러
@Controller
@RequiredArgsConstructor
@Log4j2
public class AuthController {

    // 아이디와 비밀번호를 검증하여 인증 처리를 수행하는 객체
    private final AuthenticationManager authenticationManager;

    // JWT 생성 및 검증 기능을 제공하는 객체
    private final JwtProvider jwtProvider;

    // 로그인 JSP 화면을 반환
    @GetMapping("/auth/login")
    public String loginPage() {
        return "auth/login";
    }

    // 사용자 프로필 JSP 화면을 반환
    @GetMapping("/auth/profile")
    public String profilePage() {
        return "auth/profile";
    }

    // 아이디와 비밀번호를 받아 로그인 처리 후 JWT를 반환
    @PostMapping("/api/auth/login")
    @ResponseBody
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        // 아이디 또는 비밀번호가 입력되지 않은 경우
        if (request.getUsername() == null
                || request.getUsername().isBlank()
                || request.getPassword() == null
                || request.getPassword().isBlank()) {

            // 로그인 실패 응답 객체 생성
            LoginResponse response = LoginResponse.builder()
                    .success(false)
                    .message("아이디와 비밀번호를 입력하세요.")
                    .build();

            // 400 Bad Request 상태로 응답
            return ResponseEntity
                    .badRequest()
                    .body(response);
        }

        try {
            // 입력받은 아이디와 비밀번호로 인증용 토큰 생성
            UsernamePasswordAuthenticationToken loginToken =
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    );

            // UserDetailsService와 PasswordEncoder를 이용해 실제 로그인 인증 수행
            Authentication authentication =
                    authenticationManager.authenticate(loginToken);

            // 인증된 사용자 정보를 Authentication 객체에서 추출
            CustomUserDetails user =
                    (CustomUserDetails) authentication.getPrincipal();

            // 인증된 사용자 정보를 기반으로 JWT Access Token 생성
            String accessToken =
                    jwtProvider.createToken(user);

            // 로그인 성공 응답 객체 생성
            LoginResponse response = LoginResponse.builder()
                    .success(true)
                    .message(user.getName() + "님, 로그인되었습니다.")
                    .accessToken(accessToken)
                    .tokenType("Bearer")
                    // 밀리초 단위 유효 시간을 초 단위로 변환
                    .expiresIn(jwtProvider.getExpiration() / 1000)
                    .build();

            log.info(
                    "로그인 성공: username={}",
                    user.getUsername()
            );

            // 200 OK 상태와 함께 JWT 정보 반환
            return ResponseEntity.ok(response);

        } catch (AuthenticationException e) {
            // 아이디가 없거나 비밀번호가 일치하지 않으면 실행
            log.warn(
                    "로그인 실패: username={}",
                    request.getUsername()
            );

            // 로그인 실패 응답 객체 생성
            LoginResponse response = LoginResponse.builder()
                    .success(false)
                    .message("아이디 또는 비밀번호가 올바르지 않습니다.")
                    .build();

            // 401 Unauthorized 상태로 응답
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }

    // 현재 JWT로 인증된 사용자 정보를 반환
    @GetMapping("/api/auth/me")
    @ResponseBody
    public ResponseEntity<UserResponse> me(
            // SecurityContext에 저장된 인증 사용자 객체를 자동으로 주입
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        // 인증된 사용자 정보를 응답 DTO로 변환
        UserResponse response = UserResponse.builder()
                .memberId(user.getMemberId())
                .username(user.getUsername())
                .name(user.getName())
                .role(user.getRole())
                .build();

        // 200 OK 상태와 함께 현재 사용자 정보 반환
        return ResponseEntity.ok(response);
    }
}