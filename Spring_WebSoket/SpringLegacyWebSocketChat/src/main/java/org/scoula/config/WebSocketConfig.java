package org.scoula.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/*
 * WebSocket 및 STOMP 메시지 브로커 설정 클래스입니다.
 *
 * @EnableWebSocketMessageBroker
 * - WebSocket 기반 메시지 브로커 기능을 활성화합니다.
 *
 * WebSocketMessageBrokerConfigurer
 * - 메시지 브로커와 STOMP 연결 엔드포인트를 설정합니다.
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /*
     * STOMP 메시지 브로커 설정
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        /*
         * Simple Message Broker 활성화
         *
         * /topic으로 시작하는 주소를 구독한 클라이언트에게
         * 서버가 메시지를 전달합니다.
         *
         * 예:
         * /topic/chat
         * /topic/greetings
         */
        registry.enableSimpleBroker("/topic");

        /*
         * 클라이언트가 서버로 메시지를 보낼 때 사용하는 접두어입니다.
         *
         * 클라이언트 전송 주소:
         * /app/chat
         *
         * 서버 메서드:
         * @MessageMapping("/chat")
         *
         * @MessageMapping에는 /app을 제외한 경로를 작성합니다.
         */
        registry.setApplicationDestinationPrefixes("/app");
    }

    /*
     * 클라이언트가 최초로 WebSocket 연결을 시도할
     * STOMP 엔드포인트를 등록합니다.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /*
         * WebSocket 연결 주소
         *
         * Context Path가 있는 경우:
         * ws://localhost:8080/SpringLegacyWebSocketChat/chat-app
         *
         * 루트 Context로 배포한 경우:
         * ws://localhost:8080/chat-app
         */
        registry
                .addEndpoint("/chat-app")

                /*
                 *  모든 Origin을 허용합니다.
                 * 실제 서비스에서는 허용할 도메인을 제한하는 것이 좋습니다.
                 */
                .setAllowedOriginPatterns("*");
    }
}