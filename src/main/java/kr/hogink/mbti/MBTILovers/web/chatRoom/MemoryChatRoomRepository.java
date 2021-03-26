package kr.hogink.mbti.MBTILovers.web.chatRoom;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryChatRoomRepository implements ChatRoomRepository {

    private static Map<Long, ChatRoom> store = new HashMap<>();
    private static long Sequence = 0L;


    @Override
    public ChatRoom save(ChatRoom chatRoom) {
        chatRoom.setRoomId(Sequence++);
        store.put(chatRoom.getRoomId(),chatRoom);
        return chatRoom;
    }

    @Override
    public List<ChatRoom> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Optional<ChatRoom> findByMembers(Member member1, Member member2) {
        //해당 멤버 두명이 모두 포함된 채팅방 있으면 리턴
        return store.values().stream().filter(chatRoom -> chatRoom.getMember1().equals(member1)).filter(chatRoom -> chatRoom.getMember2().equals(member2))
                .findAny();
    }

    public void clearStore() {
        store.clear();
    }

}
