package kr.hogink.mbti.MBTILovers.web.service;

import kr.hogink.mbti.MBTILovers.web.domain.ChatRoom;
import kr.hogink.mbti.MBTILovers.web.domain.Member;
import kr.hogink.mbti.MBTILovers.web.repository.ChatRoomRepository;

import java.util.List;

public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public Long make(ChatRoom chatRoom) {
        //동일한 멤버구성의 채팅방은 만들지 않음
        validateDuplicateChatRoom(chatRoom);
        chatRoomRepository.save(chatRoom);
        return chatRoom.getRoomId();
    }

    private void validateDuplicateChatRoom(ChatRoom chatRoom) {
        chatRoomRepository.findByMembers(chatRoom.getMember1(), chatRoom.getMember2()).
                ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 채팅방 입니다."); //일단 오류로 처리
                });
    }

    public ChatRoomService(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }


    /**
     * 전체 채팅방 조회
     */
    public List<ChatRoom> findMembers() {
        return chatRoomRepository.findAll();
    }
}
