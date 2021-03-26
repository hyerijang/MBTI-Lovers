package kr.hogink.mbti.MBTILovers.web.chatRoom;
import kr.hogink.mbti.MBTILovers.web.member.Member;

import java.util.List;
import java.util.Optional;


public interface ChatRoomRepository {

    //설계보면서 다시 할것
    ChatRoom save (ChatRoom chatRoom);
    List<ChatRoom> findAll();
    Optional<ChatRoom>  findByMembers(Member member1, Member member2);
}
