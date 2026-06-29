package org.scoula.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

/**
 * DelegatingFilterProxy를 Servlet Container에 등록합니다.
 *
 * 등록된 필터는 Spring의 springSecurityFilterChain Bean에
 * 실제 보안 처리를 위임합니다.
 */
public class SecurityInitializer
        extends AbstractSecurityWebApplicationInitializer {
}