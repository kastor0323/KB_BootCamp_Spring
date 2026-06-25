package org.scoula.controller;

import lombok.RequiredArgsConstructor;
import org.scoula.domain.ChatRoom;
import org.scoula.service.ChatRoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 목록 화면
    @GetMapping("/rooms")
    public String rooms(Model model) {
        return "chat/rooms";
    }

    // 모든 채팅방 목록 반환 (JSON)
    @GetMapping("/rooms/list")
    @ResponseBody
    public List<ChatRoom> roomList() {
        return chatRoomService.findAllRoom();
    }

    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomService.createChatRoom(name);
    }

    // 채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String roomDetail(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId", roomId);
        ChatRoom room = chatRoomService.findRoomById(roomId);
        model.addAttribute("roomName", room != null ? room.getName() : "알 수 없는 방");
        return "chat/room";
    }
}
