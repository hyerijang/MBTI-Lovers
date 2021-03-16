package kr.hogink.mbti.MBTILovers.web.repository;

import kr.hogink.mbti.MBTILovers.web.domain.ChatRoom;
import kr.hogink.mbti.MBTILovers.web.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemoryChatRoomRepositoryTest {

    MemoryChatRoomRepository chatRoomRepository = new MemoryChatRoomRepository();

    @AfterEach
    public void afterEach() {
        chatRoomRepository.clearStore();
    }

    @Test
    public void save() {
        ChatRoom chatRoom = new ChatRoom();
        Member member1 = new Member();
        Member member2 = new Member();
        member1.setName("김영희");
        member2.setName("김철수");
        chatRoom.setMember1(member1);
        chatRoom.setMember2(member2);
        chatRoomRepository.save(chatRoom);

//        Member member3 = new Member();
//        member3.setName("김철수");
        ChatRoom result = chatRoomRepository.findByMembers(member1, member2).get();
        assertThat(chatRoom).isEqualTo(result);
    }


}