package kr.hogink.mbti.MBTILovers.web.web;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.service.chat.room.RoomService;
import kr.hogink.mbti.MBTILovers.web.web.dto.chat.MessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Slf4j
@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final RoomService roomService;

    @MessageMapping("/chat.sendMessage") //기존 request mapping 역할
    public void sendMessage(@Payload MessageDto chatMessageDto) {
        Long rid = chatMessageDto.getRid();
        messagingTemplate.convertAndSend("/sub/chat/room/".trim() + rid, chatMessageDto);
        renewRoominfo(chatMessageDto, rid);
    }

    //최신 메세지 정보 저장
    private void renewRoominfo(MessageDto chatMessageDto, Long rid) {
        Room room = roomService.findRoomByRid(rid);
        room.setLastSentContent(chatMessageDto.getContent());
        room.setLastSentTimeAt(chatMessageDto.getSentTimeAt());
        roomService.save(room);
    }


}

