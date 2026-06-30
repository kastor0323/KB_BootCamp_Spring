package org.scoula.security.account;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.domain.MemberVO;
import org.scoula.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2 public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        log.info("DB 사용자 조회: username={}", username);

        //조회 결과에 사용자가 없으면 `UsernameNotFoundException`을 발생시키고, 있으면 해당        //MemberVO` 객체를 반환합니다.

        MemberVO member = memberRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "사용자를 찾을 수 없습니다."
                        )
                );

        return new CustomUserDetails(member);
    }
}