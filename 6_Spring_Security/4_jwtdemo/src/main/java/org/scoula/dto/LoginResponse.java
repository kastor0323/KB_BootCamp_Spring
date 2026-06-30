package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    // 로그인 성공 여부
    private boolean success;

    // 로그인 처리 결과 메시지
    private String message;

    // API 요청에 사용할 JWT Access Token
    private String accessToken;

    // 토큰 인증 방식이며 일반적으로 "Bearer" 사용
    private String tokenType;

    // Access Token의 유효 시간
    private long expiresIn;
}