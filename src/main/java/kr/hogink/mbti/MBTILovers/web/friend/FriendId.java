package kr.hogink.mbti.MBTILovers.web.friend;


import java.io.Serializable;

public class FriendId implements Serializable {

    private String uid;
    private String fid;

    public FriendId() {
    }

    public FriendId(String uid, String fid) {

        this.uid = uid;
        this.fid = fid;
    }

}