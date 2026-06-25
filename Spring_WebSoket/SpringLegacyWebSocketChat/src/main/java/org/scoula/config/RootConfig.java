package org.scoula.config;

import org.springframework.context.annotation.Configuration;

/*
 * Root WebApplicationContext 설정 클래스
 *
 * 일반적으로 다음과 같은 객체를 등록합니다.
 *
 * - Service
 * - Repository
 * - DataSource
 * - TransactionManager
 *
 * 현재 채팅 예제는 데이터베이스와 서비스 계층을 사용하지 않으므로
 * 별도의 ComponentScan 없이 빈 설정 클래스로 둡니다.
 *
 * 주의:
 * org.scoula 전체를 ComponentScan하면
 * ServletConfig와 WebSocketConfig까지 Root Context에 중복 등록될 수 있습니다.
 */
@Configuration
public class RootConfig {
}