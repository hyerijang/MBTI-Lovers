package kr.hogink.mbti.MBTILovers.web.friend;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService {
    public FriendServiceImpl(FriendRepository friendRepository) {
        this.friendRepository = friendRepository;
    }

    private final FriendRepository friendRepository;

    @Override
    public List<Friend> findAllByUid(String uid) {
        return friendRepository.findAllByUid(uid);
    }


    @Override
    public Optional<Friend> findOneByFid() {
        return Optional.empty();
    }

    @Override
    public String addFriend(Friend friend) {
        friendRepository.save(friend);
        return friend.getRelation();
    }

    @Override
    public Optional<Friend> findOneByFriendId(String uid, String fid) {
        return friendRepository.findOneByUidAndFid(uid, fid);
    }


}
