package kr.hogink.mbti.MBTILovers.web.friend;


import java.util.List;
import java.util.Optional;


public interface FriendService {

    List<Friend> findAllByUid(String uid);

    Optional<Friend> findOneByFid();

    String saveFriend(Friend friend);

    Optional<Friend> findOneByUidAndFid(String uid, String fid);
}
