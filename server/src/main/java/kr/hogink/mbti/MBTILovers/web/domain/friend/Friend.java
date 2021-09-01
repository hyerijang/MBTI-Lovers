package kr.hogink.mbti.MBTILovers.web.domain.friend;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member friendMember;
    @Enumerated(EnumType.STRING)
    private RelationType relation;
    @ManyToOne
    private Room room;

    public enum RelationType {

        FRIEND("친구"),
        REQUEST("신청"),
        RECEIVED_REQUEST("신청받음"),
        BLOCK("차단"),
        BLOCKED("차단됨"),
        NONE("아무 관계 아님");

        private final String title;

        RelationType(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }
    }

    public boolean isRoomEmpty() {
        return room == null;
    }

}

