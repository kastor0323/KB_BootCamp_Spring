package org.scoula.init;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.scoula.domain.MemberVO;
import org.scoula.repository.MemberRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

// 애플리케이션 실행 시 테스트 회원 데이터를 자동으로 생성하는 클래스
@Component
@RequiredArgsConstructor
@Log4j2
public class MemberDataInitializer implements InitializingBean {

    // 회원 데이터 조회 및 저장 기능
    private final MemberRepository memberRepository;

    // 비밀번호 암호화 기능
    private final PasswordEncoder passwordEncoder;

    // 스프링 빈 생성이 완료된 후 테스트 회원 생성 메서드를 실행
    @Override
    public void afterPropertiesSet() {
        createMemberIfNotExists(
                "member1",
                "1234",
                "일반 회원",
                "ROLE_MEMBER"
        );

        createMemberIfNotExists(
                "admin1",
                "1234",
                "관리자",
                "ROLE_ADMIN"
        );
    }

    // 동일한 아이디의 회원이 없을 경우에만 새로운 회원을 생성
    private void createMemberIfNotExists(
            String username,
            String rawPassword,
            String name,
            String role
    ) {
        // 이미 등록된 회원이면 새로 생성하지 않음
        if (memberRepository.countByUsername(username) > 0) {
            log.info("기존 회원 유지: {}", username);
            return;
        }

        // 비밀번호를 암호화한 후 회원 객체 생성
        MemberVO member = MemberVO.builder()
                .username(username)
                .password(passwordEncoder.encode(rawPassword))
                .name(name)
                .role(role)
                .enabled(true)
                .build();

        // 생성한 회원 정보를 데이터베이스에 저장
        memberRepository.insert(member);

        log.info(
                "테스트 회원 생성: username={}, role={}",
                username,
                role
        );
    }
}
