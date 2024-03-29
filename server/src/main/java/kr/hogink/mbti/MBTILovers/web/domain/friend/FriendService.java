package kr.hogink.mbti.MBTILovers.web.domain.friend;


import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;

import java.util.List;
import java.util.Optional;


public interface FriendService {

    <T> List<T> getListAllFriend(String uid);

    Optional<Friend> findOneByFid();

    void saveFriend(Friend friend);

    Optional<Friend> getFriendInfo(String uid, String fid);

    Optional<Friend> getFriendName(String uid, Room room);

    Friend getFriend(String uid, String fid);

    <T> List<T> getListRelationFriend(String uid);

    <T> List<T> getListRelationRequest(String uid);

    <T> List<T> getListRelationReceived(String uid);

    <T> List<T> getListRelationBlock(String uid);

    void removeRecord(String uid, String fid);

}
