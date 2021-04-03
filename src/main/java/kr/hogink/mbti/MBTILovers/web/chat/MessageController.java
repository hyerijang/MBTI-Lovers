package kr.hogink.mbti.MBTILovers.web.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Controller
public class MessageController {

    private static  SimpMessageSendingOperations messagingTemplate;
    private static final MessageRepositoryMem MESSAGE_REPOSITORY_MEM = new MessageRepositoryMem();



    @MessageMapping("/sub/chat/room") //기존 request mapping 역할
    public Message sendMessage(@Payload Message chatMessage){

        messagingTemplate.convertAndSend("/sub/chat/room/" +chatMessage.getRid(), chatMessage);
        MESSAGE_REPOSITORY_MEM.save(chatMessage);
        return chatMessage;
    }

    

}

