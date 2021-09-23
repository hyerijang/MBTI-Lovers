package kr.hogink.mbti.MBTILovers.web.web.dto;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.domain.friend.Friend;
import lombok.Getter;

@Getter
public class FriendListDto {

    private String uid;
    private String fid;
    private Friend.RelationType relation;
    private Room room;


    public FriendListDto(Friend friend) {
        this.uid = friend.getUid();
        this.fid = friend.getFid();
        this.relation = friend.getRelation();
        this.room = friend.getRoom();
    }
}
