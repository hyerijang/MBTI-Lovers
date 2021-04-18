package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.Room;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Data
@IdClass(FriendId.class)
public class Friend {

    @Id
    private String uid;
    @Id
    private String fid; //friend's uid
    @ManyToOne
    private Member friendMember;
    @Enumerated(EnumType.STRING)
    private RelationType relation;
    private Long rid;
    @ManyToOne
    private Room room;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Member getFriendMember() {
        return friendMember;
    }

    public void setFriendMember(Member friendMember) {
        this.friendMember = friendMember;
    }

    public enum RelationType {

        FRIEND("친구");

        private String title;

        RelationType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }


    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}

