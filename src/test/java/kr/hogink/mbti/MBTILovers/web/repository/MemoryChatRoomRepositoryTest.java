package kr.hogink.mbti.MBTILovers.web.repository;

import kr.hogink.mbti.MBTILovers.web.chatRoom.ChatRoom;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.chatRoom.MemoryChatRoomRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        chatRoomRepository.createChatRoom(chatRoom);

//        Member member3 = new Member();
//        member3.setName("김철수");
        ChatRoom result = chatRoomRepository.findById(member1, member2).get();
        assertThat(chatRoom).isEqualTo(result);
    }


}