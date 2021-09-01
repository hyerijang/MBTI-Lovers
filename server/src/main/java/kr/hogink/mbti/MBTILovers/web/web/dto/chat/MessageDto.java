package kr.hogink.mbti.MBTILovers.web.web.dto.chat;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

//firebase에 저장
@Getter
@Setter
public class MessageDto {

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

