package kr.hogink.mbti.MBTILovers.web.chat;

import javax.annotation.PostConstruct;
import java.util.*;

public class ChatRepository {

    private Map<String, ChatRoom> chatRecord; //메모리에 저장
    @PostConstruct
    private void init() {
        chatRecord = new LinkedHashMap<>();
    }

    public List<ChatMessage> findAllChat(){

        List chatMessageList = new ArrayList(chatRecord.values());
        Collections.reverse(chatMessageList);
        return chatMessageList;
    }

}
