package kr.hogink.mbti.MBTILovers.web.chat.message;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

//firebase에 저장
@Getter
@Setter
public class Message {

    private Long rid; //외래키
    private MessageType type;
    private String content;
    private String sender;
    private String senderUid;
    private Timestamp sentTimeAt;
    
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}

