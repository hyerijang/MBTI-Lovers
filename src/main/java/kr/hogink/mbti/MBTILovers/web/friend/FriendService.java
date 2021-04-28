package kr.hogink.mbti.MBTILovers.web.friend;


import kr.hogink.mbti.MBTILovers.web.chat.room.Room;

import java.util.List;
import java.util.Optional;


public interface FriendService {

    <T> List<T> findAllByUid(String uid);

    Optional<Friend> findOneByFid();

    void saveFriend(Friend friend);

    Optional<Friend> getFriendInfo(String uid, String fid);

    Optional<Friend> getFriendName(String uid, Room room);

}
