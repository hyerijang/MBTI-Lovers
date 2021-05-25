package kr.hogink.mbti.MBTILovers.web.friend;

import kr.hogink.mbti.MBTILovers.web.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
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


    @Override
    public Friend getFriend(String uid, String fid) {
        Optional<Friend> f = getFriendInfo(uid, fid);
        //이미 존재하는 관계
        if (f.isPresent())
            return f.get();

        log.info("새로운 프렌드 객체를 생성합니다.");
        Friend friend = new Friend();
        friend.setUid(uid);
        friend.setFid(fid);
        return friend;
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
