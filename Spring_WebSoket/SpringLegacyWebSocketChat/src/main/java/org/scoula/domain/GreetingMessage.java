package org.scoula.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 채팅방 입장 메시지 DTO입니다.
 *
 * 클라이언트가 전송하는 JSON:
 *
 * {
 *   "name": "홍길동"
 * }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreetingMessage {

    /*
     * 채팅방에 입장한 사용자 이름
     */
    private String name;
}