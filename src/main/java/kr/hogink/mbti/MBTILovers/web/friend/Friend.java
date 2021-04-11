package kr.hogink.mbti.MBTILovers.web.friend;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@Entity
@IdClass(FriendId.class)
public class Friend {

    @Id
    private String uid;
    @Id
    private String fid; //friend's uid
    private String relation;
    private Long rid;

}
