package org.scoula.mapper;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.scoula.config.RootConfig;
import org.scoula.domain.PortalUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
class PortalUserMapperTests {

    @Autowired
    private PortalUserMapper portalUserMapper;

    @Test
    void findMemberUser() {
        PortalUserVO user =
                portalUserMapper.findByUserId("member1");

        assertNotNull(user);
        assertEquals("member1", user.getUserId());
        assertTrue(
                user.getRoles().contains("ROLE_MEMBER")
        );

        log.info("member1 조회 결과: {}", user);
    }

    @Test
    void findAdminUser() {
        PortalUserVO user =
                portalUserMapper.findByUserId("admin1");

        assertNotNull(user);
        assertTrue(
                user.getRoles().contains("ROLE_MEMBER")
        );
        assertTrue(
                user.getRoles().contains("ROLE_ADMIN")
        );

        log.info("admin1 조회 결과: {}", user);
    }
}