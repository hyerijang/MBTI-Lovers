package kr.hogink.mbti.MBTILovers.web.friend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@Transactional
class FriendServiceTest {

    @Autowired
    FriendService friendService;

    @Autowired
    FriendRepository friendRepository;

    @Test
    void 친구추가() {
        friendService.addFriend("me","ff");
    }


}