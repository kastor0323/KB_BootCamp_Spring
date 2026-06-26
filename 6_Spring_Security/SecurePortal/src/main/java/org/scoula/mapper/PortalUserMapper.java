package org.scoula.mapper;

import org.apache.ibatis.annotations.Param;
import org.scoula.domain.PortalUserVO;

public interface PortalUserMapper {

    /**
     * 사용자 아이디를 기준으로
     * 회원과 권한 정보를 조회합니다.
     */
    PortalUserVO findByUserId(
            @Param("userId") String userId
    );
}