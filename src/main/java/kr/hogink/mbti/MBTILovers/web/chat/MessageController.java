package kr.hogink.mbti.MBTILovers.web.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class MessageController {

    private final SimpMessageSendingOperations messagingTemplate;


    @MessageMapping("/sub/chat/room") //기존 request mapping 역할
    public Message sendMessage(@Payload Message chatMessage){
        chatMessage.setrid("rid"); //임시
        messagingTemplate.convertAndSend("/sub/chat/room/" +chatMessage.getrid(), chatMessage);
        return chatMessage;
    }

    

}

