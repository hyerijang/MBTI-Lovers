package kr.hogink.mbti.MBTILovers.web.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatRoomController {

    final static ChatRoomRepository chatRoomRepository = new ChatRoomRepository();

    //채팅 리스트화면
    @GetMapping(value = "/chat/room")
    public String list(Model model) {
        return "chat/chatRoom";
    }

    // 모든 채팅방 목록 반환
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> room() {
        return chatRoomRepository.findAllRoom();
    }


    // 채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam String name) {
        return chatRoomRepository.createChatRoom(name);
    }


    //채팅방 입장화면 {roomid}를 통해 입장
    @GetMapping(value = "/chat/enter/{roomId}")
    public String createFrom(Model model, @PathVariable String roomId) {
        model.addAttribute("roomId",roomId);
        System.out.println(roomId);
        return "chat/roomdetail";
    }


}