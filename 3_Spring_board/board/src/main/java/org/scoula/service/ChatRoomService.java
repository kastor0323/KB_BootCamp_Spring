package org.scoula.service;

import lombok.extern.slf4j.Slf4j;
import org.scoula.domain.ChatRoom;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class ChatRoomService {

    private Map<String, ChatRoom> chatRoomMap = new LinkedHashMap<>();


    public List<ChatRoom> findAllRoom() {
        // 채팅방 생성순으로 반환
        List<ChatRoom> chatRooms = new ArrayList<>(chatRoomMap.values());
        Collections.reverse(chatRooms);
        return chatRooms;
    }

    public ChatRoom findRoomById(String roomId) {
        return chatRoomMap.get(roomId);
    }

    public ChatRoom createChatRoom(String name) {
        ChatRoom chatRoom = ChatRoom.create(name);
        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }
}
