package kr.hogink.mbti.MBTILovers.web.domain.chat;

import kr.hogink.mbti.MBTILovers.web.web.dto.chat.MessageDto;

import java.util.*;

public class MemoryMessageRepository {

    private static final Map<Long, MessageDto> chatRecord = new HashMap<>();
    //메모리에 저장
    private static final Long sequence = 0L;


    public void save(MessageDto messageDto) {
        System.out.println("메세지 " + messageDto + "저장");
    }

    public MessageDto findByMsgId(Long MsgId) {
        return chatRecord.get(MsgId);
    }

    public List<MessageDto> findAllChat() {

        List chatMessageList = new ArrayList(chatRecord.values());
        Collections.reverse(chatMessageList);
        return chatMessageList;
    }

    public void clear() {
        chatRecord.clear();
    }

}
