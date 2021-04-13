package kr.hogink.mbti.MBTILovers.web.chat;

import java.util.List;

public interface RoomService {

    List<Room> findAllRoom();

    void createChatRoom(Room room);

    Room findRoomByRid(Long rid);


}
