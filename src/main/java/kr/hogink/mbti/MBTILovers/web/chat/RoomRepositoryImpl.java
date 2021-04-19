package kr.hogink.mbti.MBTILovers.web.chat;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface RoomRepositoryImpl extends JpaRepository<Room, Long>, RoomRepository {

    @Override
    List<Room> findAll();

    @Override
    Room save(Room room);
}
