package kr.hogink.mbti.MBTILovers.web.friend;

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
    @Enumerated(EnumType.STRING)
    private RelationType relation;
    private Long rid;

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


}

