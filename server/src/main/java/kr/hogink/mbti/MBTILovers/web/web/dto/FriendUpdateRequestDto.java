package kr.hogink.mbti.MBTILovers.web.web.dto;

import kr.hogink.mbti.MBTILovers.web.domain.friend.Friend;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FriendUpdateRequestDto {
    private String uid;
    private Friend.RelationType relation;
    private Member friendMember;

    public FriendUpdateRequestDto(String uid, Friend.RelationType relation, Member friendMember) {
        this.uid = uid;
        this.relation = relation;
        this.friendMember = friendMember;
    }

    @Builder
    public Friend toEntity() {
        return Friend.builder()
                .uid(uid)
                .friendMember(friendMember)
                .relation(relation)
                .build();
    }
}
