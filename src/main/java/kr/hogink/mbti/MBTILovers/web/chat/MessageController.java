package kr.hogink.mbti.MBTILovers.web.chat;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;


@Slf4j
@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    public MessageController(SimpMessagingTemplate template) {
        this.messagingTemplate = template;
    }



    @MessageMapping("/chat.sendMessage") //기존 request mapping 역할
    public void sendMessage(@Payload Message chatMessage){
        System.out.println("현재 방 : " + chatMessage.getRid());
        messagingTemplate.convertAndSend("/sub/chat/room/0",chatMessage);
    }

    

}

