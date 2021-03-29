package kr.hogink.mbti.MBTILovers.web.chatRoom;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryChatRoomRepository implements ChatRoomRepository {

    private static Map<Long, ChatRoom> chatRoomMap = new LinkedHashMap<>();
    private static long Sequence = 0L;


    @Override
    public ChatRoom createChatRoom(ChatRoom chatRoom) {
        chatRoom.setRoomId(Sequence++);
        chatRoomMap.put(chatRoom.getRoomId(),chatRoom);
        return chatRoom;
    }


    @Override
    public List<ChatRoom> findAllRoom() {
        return new ArrayList<>(chatRoomMap.values());
    }

    @Override
    public Optional<ChatRoom> findById(Member member1, Member member2) {
        //해당 멤버 두명이 모두 포함된 채팅방 있으면 리턴
        return chatRoomMap.values().stream().filter(chatRoom -> chatRoom.getMember1().equals(member1)).filter(chatRoom -> chatRoom.getMember2().equals(member2))
                .findAny();
    }

    public void clearStore() {
        chatRoomMap.clear();
    }

}
