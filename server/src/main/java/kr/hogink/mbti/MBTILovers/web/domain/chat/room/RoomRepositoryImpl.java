package kr.hogink.mbti.MBTILovers.web.domain.chat.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface RoomRepositoryImpl extends JpaRepository<Room, Long>, RoomRepository {

    @Override
    List<Room> findAll();

    @Override
    Room save(Room room);
}
