package kr.hogink.mbti.MBTILovers.web.chat.message;

import kr.hogink.mbti.MBTILovers.web.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.chat.room.RoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;

    private final RoomService roomService;

    public MessageController(SimpMessagingTemplate messagingTemplate, RoomService roomService) {
        this.messagingTemplate = messagingTemplate;
        this.roomService = roomService;
    }

    @MessageMapping("/chat.sendMessage") //기존 request mapping 역할
    public void sendMessage(@Payload Message chatMessage) {
        Long rid = chatMessage.getRid();
        System.out.println("현재 방 : " + rid);
        messagingTemplate.convertAndSend("/sub/chat/room/".trim() + rid, chatMessage);

        Room room = roomService.findRoomByRid(rid);
        room.setLastSentContent(chatMessage.getContent());
        room.setLastSentTimeAt(chatMessage.getSentTimeAt());
        roomService.save(room);
    }


}

