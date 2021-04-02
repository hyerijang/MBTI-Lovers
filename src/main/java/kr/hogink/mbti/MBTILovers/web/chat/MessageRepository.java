package kr.hogink.mbti.MBTILovers.web.chat;

import javax.annotation.PostConstruct;
import java.util.*;

public class MessageRepository {

    private Map<String, Room> chatRecord; //메모리에 저장
    @PostConstruct
    private void init() {
        chatRecord = new LinkedHashMap<>();
    }

    public List<Message> findAllChat(){

        List chatMessageList = new ArrayList(chatRecord.values());
        Collections.reverse(chatMessageList);
        return chatMessageList;
    }

}
