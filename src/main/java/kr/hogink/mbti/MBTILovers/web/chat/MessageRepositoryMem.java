package kr.hogink.mbti.MBTILovers.web.chat;

import java.util.*;

public class MessageRepositoryMem {

    private static final Map<Long, Message> chatRecord = new HashMap<>();
    //메모리에 저장
    private static final Long sequence = 0L;


    public void save(Message message) {
        System.out.println("메세지 " + message + "저장");
    }

    public Message findByMsgId(Long MsgId) {
        return chatRecord.get(MsgId);
    }

    public List<Message> findAllChat() {

        List chatMessageList = new ArrayList(chatRecord.values());
        Collections.reverse(chatMessageList);
        return chatMessageList;
    }

    public void clear() {
        chatRecord.clear();
    }

}
