package kr.hogink.mbti.MBTILovers.web.friend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class FriendServiceTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Test
    void 친구추가() {
        Friend friend = new Friend();
        friend.setUid("홍길동2");
        friend.setFid("임꺽정2");
        friend.setRelation("친구");
        friendService.addFriend(friend);
    }


}