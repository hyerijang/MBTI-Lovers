package kr.hogink.mbti.MBTILovers.web.chat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rid;
    private String name; // 채팅방 이름
    private Timestamp lastSentTimeAt;
    private String lastSentContent;

    public String getLastSentContent() {
        return lastSentContent;
    }

    public void setLastSentContent(String lastSentContent) {
        this.lastSentContent = lastSentContent;
    }

    public Timestamp getLastSentTimeAt() {
        return lastSentTimeAt;
    }

    public void setLastSentTimeAt(Timestamp lastSentTimeAt) {
        this.lastSentTimeAt = lastSentTimeAt;
    }

    public Long getRid() {
        return rid;
    }

    public void setRid(Long rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
