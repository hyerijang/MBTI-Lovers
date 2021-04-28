package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FriendServiceImpl implements FriendService {

    private final FriendRepository friendRepository;
    private final MemberRepository memberRepository;

    public FriendServiceImpl(FriendRepository friendRepository, MemberRepository memberRepository) {
        this.friendRepository = friendRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public List<Friend> findAllByUid(String uid) {
        return friendRepository.findAllByUid(uid);
    }


    @Override
    public Optional<Friend> findOneByFid() {
        return Optional.empty();
    }

    @Override
    public void saveFriend(Friend friend) {
        validateSelfFriend(friend);
        setAndSaveFriend(friend);
        setAndSaveFriend(reverseFriend(friend));
    }

    @Override
    public Optional<Friend> getFriendInfo(String uid, String fid) {
        return friendRepository.findOneByUidAndFid(uid, fid);
    }

    @Override
    public Optional<Friend> getFriendName(String uid, Room room) {
        return friendRepository.findOneByUidAndRoom(uid, room);
    }

    Friend reverseFriend(Friend friend) {
        Friend reverse = new Friend();
        reverse.setUid(friend.getFid());
        reverse.setFid(friend.getUid());
        reverse.setRelation(friend.getRelation()); //일단 동일한 관계 되도록 함
        if (friend.getRoom() != null)
            reverse.setRoom(friend.getRoom());
        return reverse;
    }

    private void validateSelfFriend(Friend friend) {
        String uid = friend.getUid().trim();
        String fid = friend.getFid().trim();
        if (uid.equals(fid))
            throw new IllegalStateException("자신과는 친구가 될 수 없습니다.");
    }

    private void setAndSaveFriend(Friend friend) {
        friend.setFriendMember(memberRepository.findByUid(friend.getFid()).get());
        friendRepository.save(friend);
    }
}
