package kr.hogink.mbti.MBTILovers.web.service.chat.room;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.domain.chat.room.RoomRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class RoomServiceImpl implements RoomService {


    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public List<Room> findAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public void createChatRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Room findRoomByRid(Long rid) {
        return roomRepository.findRoomByRid(rid).get();
    }

    @Override
    public void save(Room room) {
        roomRepository.save(room);
    }
}

