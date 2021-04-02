package kr.hogink.mbti.MBTILovers.web.chat;

import java.util.UUID;

public class Room {

    private String rid;
    private String name;

    Room() {
        this.rid = UUID.randomUUID().toString();
    }


    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

