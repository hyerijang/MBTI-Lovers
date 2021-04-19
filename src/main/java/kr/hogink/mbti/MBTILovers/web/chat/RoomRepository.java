package kr.hogink.mbti.MBTILovers.web.chat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface RoomRepository {

    List<Room> findAll();

    Optional<Room> findRoomByRid(Long rid);

    Room save(Room room);
}
