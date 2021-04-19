package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.Room;
import kr.hogink.mbti.MBTILovers.web.chat.RoomService;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
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
    void 친구추가() {
        //회원가입
        String member1Uid = "user1";
        String member2Uid = "user2";
        Member member1 = new Member(member1Uid);
        Member member2 = new Member(member2Uid);
        memberService.join(member1);
        memberService.join(member2);

        //친구추가
        Friend friend = new Friend();
        friend.setUid(member1Uid);
        friend.setFid(member2Uid);
        friend.setRelation(Friend.RelationType.FRIEND);
        //        Room room = new Room();
        //        roomService.createChatRoom(room);
        //        friend.setRoom(room);
        friendService.saveFriend(friend);
        System.out.println(friend.getFriendMember().getMbti());
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