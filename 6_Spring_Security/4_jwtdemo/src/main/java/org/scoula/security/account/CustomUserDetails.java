package org.scoula.security.account;

import lombok.Getter;
import org.scoula.domain.MemberVO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private final Long memberId;
    private final String username;
    private final String password;
    private final String name;
    private final String role;
    private final boolean enabled;

    // MemberVO의 회원 정보를 Spring Security가 사용할 수 있는 형태로 변환
    public CustomUserDetails(MemberVO member) {
        this.memberId = member.getMemberId();
        this.username = member.getUsername();
        this.password = member.getPassword();
        this.name = member.getName();
        this.role = member.getRole();
        this.enabled = member.isEnabled();
    }

    // 현재 사용자가 가진 권한 목록 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(role)
        );
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 활성화 여부 반환
    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
