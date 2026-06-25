package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 일반 채팅 메시지 DTO입니다.
 *
 * 클라이언트가 서버로 전송하는 JSON:
 *
 * {
 *   "name": "홍길동",
 *   "content": "안녕하세요."
 * }
 *
 * 서버가 구독자에게 보내는 JSON:
 *
 * {
 *   "name": "홍길동",
 *   "content": "안녕하세요.",
 *   "sentAt": "14:25"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    /*
     * 메시지를 보낸 사용자 이름
     */
    private String name;

    /*
     * 채팅 메시지 내용
     */
    private String content;

    /*
     * 서버에서 설정하는 메시지 전송 시각
     *
     * 클라이언트가 처음 전송할 때는 null이며,
     * ChatController가 현재 시각을 설정합니다.
     */
    private String sentAt;
}