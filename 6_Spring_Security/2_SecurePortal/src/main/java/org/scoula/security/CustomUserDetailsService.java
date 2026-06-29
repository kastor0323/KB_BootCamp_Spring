package org.scoula.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.domain.PortalUserVO;
import org.scoula.mapper.PortalUserMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final PortalUserMapper portalUserMapper;

    /**
     * 로그인 화면에서 전달된 아이디로
     * DB 회원과 권한 정보를 조회합니다.
     */
    @Override
    public UserDetails loadUserByUsername(
            String username
    ) throws UsernameNotFoundException {

        log.info("로그인 사용자 조회: {}", username);

        PortalUserVO portalUser =
                portalUserMapper.findByUserId(username);

        if (portalUser == null) {
            throw new UsernameNotFoundException(
                    "존재하지 않는 사용자입니다."
            );
        }

        List<SimpleGrantedAuthority> authorities =
                portalUser.getRoles()
                        .stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        /*
         * DB의 PortalUserVO를 Spring Security가 사용하는
         * UserDetails 객체로 변환합니다.
         */
        return User.builder()
                .username(portalUser.getUserId())
                .password(portalUser.getPassword())
                .disabled(!portalUser.isEnabled())
                .authorities(authorities)
                .build();
    }
}