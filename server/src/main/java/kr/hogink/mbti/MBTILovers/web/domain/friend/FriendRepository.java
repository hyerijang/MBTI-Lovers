package kr.hogink.mbti.MBTILovers.web.domain.friend;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    <T> List<T> findAllByUid(String uid);

    Friend save(Friend friend);

    Optional<Friend> findOneByUidAndFid(String uid, String fid);

    Optional<Friend> findOneByUidAndRoom(String uid, Room room);

    <T> List<T> findAllByUidAndRelation(String uid, Friend.RelationType relationType);

    void deleteByUidAndFid(String uid, String fid);
}
