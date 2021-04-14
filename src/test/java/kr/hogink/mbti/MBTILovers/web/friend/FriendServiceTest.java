package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.Room;
import kr.hogink.mbti.MBTILovers.web.chat.RoomService;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Transactional
class FriendServiceTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    RoomService roomService;

    @Test
    void 친구추가() {
        Friend friend = new Friend();
        friend.setUid("1");
        friend.setFid("2");
        friend.setRelation(Friend.RelationType.FRIEND);
        Room room = new Room();
        roomService.createChatRoom(room);
        friend.setRoom(room);
        friendService.saveFriend(friend);
        System.out.println(friend.getFriendMember().getMbti());
    }

    @Test
    void 자신은친구추가불가() {
        Friend friend = new Friend();
        friend.setUid("5");
        friend.setFid("5");
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> friendService.saveFriend(friend));
        assertThat(e.getMessage()).isEqualTo("자신과는 친구가 될 수 없습니다.");
    }


}