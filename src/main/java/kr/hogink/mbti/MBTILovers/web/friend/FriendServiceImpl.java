package kr.hogink.mbti.MBTILovers.web.friend;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
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
    public String saveFriend(Friend friend) {
        if (friend.getUid().trim() == friend.getFid().trim())
            return "자신과는 친구 될수 없음";
        else {
            friendRepository.save(friend);
            Friend friendR = reverseFriend(friend);
            friendRepository.save(friendR);
            return friend.getRelation().toString();
        }

    }

    @Override
    public Optional<Friend> findOneByUidAndFid(String uid, String fid) {
        return friendRepository.findOneByUidAndFid(uid, fid);
    }

    Friend reverseFriend(Friend friend) {
        Friend reverse = new Friend();
        reverse.setUid(friend.getFid());
        reverse.setFid(friend.getUid());
        reverse.setRelation(friend.getRelation()); //일단 동일한 관계 되도록 함
        if (friend.getRid() != null)
            reverse.setRid(friend.getRid());
        return reverse;
    }

}
