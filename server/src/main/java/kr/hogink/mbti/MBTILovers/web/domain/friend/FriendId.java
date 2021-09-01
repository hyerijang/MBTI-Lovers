package kr.hogink.mbti.MBTILovers.web.domain.friend;


import lombok.Data;

import java.io.Serializable;

@Data
public class FriendId implements Serializable {

    private String uid;
    private String fid;


}