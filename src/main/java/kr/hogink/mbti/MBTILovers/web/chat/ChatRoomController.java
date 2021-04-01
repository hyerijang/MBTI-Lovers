package kr.hogink.mbti.MBTILovers.web.chat;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ChatRoomController {

    final static ChatRoomRepository chatRoomRepository = new ChatRoomRepository();

    //채팅홈
    @GetMapping(value = "/chatHome")
    public String chatHome(Model model) {
        return "chatHome";
    }


    // 모든 채팅방 목록 반환
    @GetMapping(value = "/chatList")
    public String list(Model model) {
        chatRoomRepository.createChatRoom("방1"); //임시
        chatRoomRepository.createChatRoom("방2"); //임시
        List<ChatRoom> chatRooms = chatRoomRepository.findAllRoom();
        model.addAttribute("chatRooms", chatRooms);
        return "chat/chatList";
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
        ChatRoom room = chatRoomRepository.findRoomById(roomId);
        model.addAttribute("roomId",room.getRoomId());
        model.addAttribute("name",room.getName());
        System.out.println("room id :"+  roomId);
        return "chat/roomdetail";
    }


}