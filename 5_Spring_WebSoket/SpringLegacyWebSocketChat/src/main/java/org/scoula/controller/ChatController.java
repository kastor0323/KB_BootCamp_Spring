package org.scoula.controller;

import lombok.extern.log4j.Log4j2;
import org.scoula.domain.ChatMessage;
import org.scoula.domain.GreetingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/*
 * STOMP 메시지를 처리하는 Controller입니다.
 *
 * 일반 HTTP 요청을 처리하는 @GetMapping이 아니라
 * STOMP 메시지를 처리하는 @MessageMapping을 사용합니다.
 */
@Controller
@Log4j2
public class ChatController {

    /*
     * 입장 메시지 처리
     *
     * 클라이언트 전송 주소:
     * /app/hello
     *
     * setApplicationDestinationPrefixes("/app")가 설정되어 있으므로
     * @MessageMapping에는 /app을 제외한 "/hello"만 작성합니다.
     *
     * 처리 결과 전송 주소:
     * /topic/greetings
     *
     * /topic/greetings를 구독 중인 모든 클라이언트가
     * 반환된 메시지를 받습니다.
     */
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public GreetingMessage greeting(GreetingMessage message) {

        /*
         * 이름이 없거나 공백만 전달된 경우를 방지합니다.
         */
        if (message == null
                || message.getName() == null
                || message.getName().trim().isEmpty()) {

            return new GreetingMessage("이름 없는 사용자");
        }

        /*
         * 문자열 앞뒤 공백 제거
         */
        message.setName(message.getName().trim());

        log.info("입장 메시지: {}", message);

        return message;
    }

    /*
     * 일반 채팅 메시지 처리
     *
     * 클라이언트 전송 주소:
     * /app/chat
     *
     * 처리 결과 전송 주소:
     * /topic/chat
     */
    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public ChatMessage chat(ChatMessage message) {

        /*
         * 메시지 객체가 null인 경우를 방어합니다.
         *
         * 정상적인 STOMP 요청에서는 거의 발생하지 않지만,
         * 안전한 처리를 위해 검사합니다.
         */
        if (message == null) {
            return new ChatMessage(
                    "알 수 없음",
                    "",
                    getCurrentTime()
            );
        }

        /*
         * 사용자 이름 검증
         */
        if (message.getName() == null
                || message.getName().trim().isEmpty()) {

            message.setName("이름 없는 사용자");

        } else {
            message.setName(message.getName().trim());
        }

        /*
         * 메시지 내용 검증
         */
        if (message.getContent() == null) {
            message.setContent("");

        } else {
            message.setContent(message.getContent().trim());
        }

        /*
         * 메시지 전송 시각은 클라이언트가 아닌
         * 서버 시각을 기준으로 설정합니다.
         */
        message.setSentAt(getCurrentTime());

        log.info("채팅 메시지: {}", message);

        return message;
    }

    /*
     * 현재 시각을 HH:mm 형식으로 반환합니다.
     *
     * 예:
     * 09:05
     * 14:25
     */
    private String getCurrentTime() {
        return LocalTime.now()
                .format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}