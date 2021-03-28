package kr.hogink.mbti.MBTILovers.web.chatRoom;
import kr.hogink.mbti.MBTILovers.web.member.Member;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


public interface ChatRoomRepository {

    List<ChatRoom> findAllRoom();
    Optional<ChatRoom> findById(Member member1, Member member2);
    ChatRoom createChatRoom(ChatRoom chatRoom);
}
