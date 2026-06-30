package org.scoula.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    // 회원 고유 번호
    private Long memberId;

    // 로그인에 사용하는 사용자 아이디
    private String username;

    // 사용자 이름
    private String name;

    // 사용자 권한
    private String role;
}