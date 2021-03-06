package kr.hogink.mbti.MBTILovers.web.chat.room;

import java.util.List;

public interface RoomService {

    List<Room> findAllRoom();

    void createChatRoom(Room room);

    Room findRoomByRid(Long rid);

    void save(Room room);
}
