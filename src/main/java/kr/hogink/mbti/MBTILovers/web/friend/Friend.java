package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.room.Room;
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

    public enum RelationType {

        FRIEND("친구");

        private final String title;

        RelationType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

}

