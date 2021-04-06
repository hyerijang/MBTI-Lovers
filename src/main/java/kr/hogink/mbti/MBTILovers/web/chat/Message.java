package kr.hogink.mbti.MBTILovers.web.chat;

import java.time.LocalDateTime;

//firebase에 저장
public class Message {

    private Long rid; //외래키
    private MessageType type;
    private String content;
    private String sender;
    private String senderUid;
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


    public String getSenderUid() {
        return senderUid;
    }

    public void setSenderUid(String senderUid) {
        this.senderUid = senderUid;
    }
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}

