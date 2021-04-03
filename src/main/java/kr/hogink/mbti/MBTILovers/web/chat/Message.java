package kr.hogink.mbti.MBTILovers.web.chat;

import javax.persistence.Id;
import java.time.LocalDateTime;

public class Message {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long msgid; //messag id
    private Long rid; //외래키
    private MessageType type;
    private String content;
    private String sender;
    private LocalDateTime sentTimeAt;


    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getSentTimeAt() {
        return sentTimeAt;
    }

    public void setSentTimeAt(LocalDateTime sentTimeAt) {
        this.sentTimeAt = sentTimeAt;
    }

    public Long getMsgId() {
        return msgid;
    }

    public void setMsgId(Long msgid) {
        this.msgid = msgid;
    }


    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}

