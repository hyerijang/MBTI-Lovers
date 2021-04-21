package kr.hogink.mbti.MBTILovers.web.chat.message;

import kr.hogink.mbti.MBTILovers.web.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.chat.room.RoomService;
import lombok.extern.log4j.Log4j2;
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
        messagingTemplate.convertAndSend("/sub/chat/room/".trim() + rid, chatMessage);
        renewRoominfo(chatMessage, rid);
    }

    //최신 메세지 정보 저장
    private void renewRoominfo(Message chatMessage, Long rid) {
        Room room = roomService.findRoomByRid(rid);
        room.setLastSentContent(chatMessage.getContent());
        room.setLastSentTimeAt(chatMessage.getSentTimeAt());
        roomService.save(room);
    }


}

