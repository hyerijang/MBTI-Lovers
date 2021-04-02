package kr.hogink.mbti.MBTILovers.web.chat;

public class Message {

    private MessageType type;
    private String content;
    private String sender;
    private String rid;

    public String getrid() {
        return rid;
    }

    public void setrid(String rid) {
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

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}

