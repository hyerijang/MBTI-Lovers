package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.room.RoomService;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    FriendService friendService;

    @Autowired
    MemberService memberService;

    @Autowired
    RoomService roomService;

    @Test
    void 친구수락() {
        //회원가입
        String member1Uid = "user1";
        String member2Uid = "user2";
        Member member1 = new Member(member1Uid);
        Member member2 = new Member(member2Uid);
        memberService.join(member1);
        memberService.join(member2);

        //member1이 친구수락함
        Friend friend = new Friend();
        friend.setUid(member1Uid);
        friend.setFid(member2Uid);
        friend.setRelation(Friend.RelationType.FRIEND);
        friendService.saveFriend(friend);
        
        //member2 친구리스트에 member1이 추가 됨
        assertThat(friendService.getFriend(member2Uid,member1Uid).getRelation()).isEqualTo(Friend.RelationType.FRIEND);
    }

    @Test
    void 자신은친구추가불가() {
        Friend friend = new Friend();
        friend.setUid("self");
        friend.setFid("self");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> friendService.saveFriend(friend));
        assertThat(e.getMessage()).isEqualTo("자신과는 친구가 될 수 없습니다.");
    }


}