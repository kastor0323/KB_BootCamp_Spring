package org.scoula.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class PortalUserVO {

    // 로그인 아이디
    private String userId;

    // BCrypt 암호화 비밀번호
    private String password;

    // 사용자 이름
    private String userName;

    // 계정 활성화 여부
    private boolean enabled;

    // 계정 생성일
    private LocalDateTime createdAt;

    // ROLE_MEMBER, ROLE_ADMIN 등의 권한 목록
    private List<String> roles = new ArrayList<>();
}