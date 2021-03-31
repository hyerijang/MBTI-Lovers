package kr.hogink.mbti.MBTILovers.web.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;


    @MessageMapping("/sub/chat/room") //기존 request mapping 역할
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage){
        chatMessage.setRoomId("roomid"); //임시
        messagingTemplate.convertAndSend("/sub/chat/room/" +chatMessage.getRoomId(), chatMessage);
        return chatMessage;
    }

    

}

