package kr.hogink.mbti.MBTILovers.web.service.chat.room;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;

import java.util.List;

public interface RoomService {

    List<Room> findAllRoom();

    void createChatRoom(Room room);

    Room findRoomByRid(Long rid);

    void save(Room room);
}
