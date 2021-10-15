package kr.hogink.mbti.MBTILovers.web.domain.friend;

import kr.hogink.mbti.MBTILovers.web.domain.chat.room.Room;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.domain.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static kr.hogink.mbti.MBTILovers.web.domain.friend.Friend.RelationType.*;

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
    public List<Friend> getListAllFriend(String uid) {
        return friendRepository.findAllByUid(uid);
    }


    @Override
    public Optional<Friend> findOneByFid() {
        return Optional.empty();
    }

    @Override
    public void saveFriend(Friend friend) {
        validateSelfFriend(friend);
        setFriendMember(friend);
        friendRepository.save(friend);

        Friend opponent = reverseFriend(friend);
        setFriendMember(opponent);
        log.info("상대방 관계: " + opponent.getRelation());
        friendRepository.save(opponent);
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

    @Override
    public <T> List<T> getListRelationFriend(String uid) {
        return friendRepository.findAllByUidAndRelation(uid, FRIEND);
    }

    @Override
    public <T> List<T> getListRelationRequest(String uid) {
        return friendRepository.findAllByUidAndRelation(uid, REQUEST);
    }

    @Override
    public <T> List<T> getListRelationReceived(String uid) {
        return friendRepository.findAllByUidAndRelation(uid, RECEIVED_REQUEST);
    }

    @Override
    public <T> List<T> getListRelationBlock(String uid) {
        return friendRepository.findAllByUidAndRelation(uid, BLOCK);
    }

    @Override
    public void removeRecord(String uid, String fid) {
        Optional<Friend> friend = friendRepository.findOneByUidAndFid(uid, fid);
        if (friend.isPresent()) {
            if (friend.get().getRelation() == REQUEST) {
                log.info("해당 기록을 삭제합니다.");
                friendRepository.deleteByUidAndFid(uid, fid);

                Optional<Friend> opponent = friendRepository.findOneByUidAndFid(fid, uid);
                if (opponent.isPresent())
                    friendRepository.deleteByUidAndFid(fid, uid);
            }
        }

    }


    Friend reverseFriend(Friend friend) {
        Friend opponent = new Friend();
        opponent.setUid(friend.getFid());
        opponent.setFid(friend.getUid());
        if (friend.getRoom() != null)
            opponent.setRoom(friend.getRoom());

        //관계 설정
        if (friend.getRelation() == FRIEND) {
            log.info("친구 신청 수락");
            opponent.setRelation(FRIEND);
        } else if (friend.getRelation() == REQUEST) {
            log.info("친구 신청");
            opponent.setRelation(RECEIVED_REQUEST);
        } else if (friend.getRelation() == BLOCK) {
            log.info("친구 차단");
            opponent.setRelation(BLOCKED);
        } else if (friend.getRelation() == NONE) {
            log.info("친구 차단 취소");
            opponent.setRelation(NONE);
        }
        return opponent;
    }

    private void validateSelfFriend(Friend friend) {
        String uid = friend.getUid().trim();
        String fid = friend.getFid().trim();
        if (uid.equals(fid))
            throw new IllegalStateException("자신과는 친구가 될 수 없습니다.");
    }

    private void setFriendMember(Friend friend) {
        if (friend.getFriendMember() == null) {
            Optional<Member> otherUser = memberRepository.findByUid(friend.getFid());
            if (otherUser.isPresent()) {
                friend.setFriendMember(otherUser.get());
            } else {
                log.warn("존재하지 않는 유저에게 친구 신청을 할 수 없습니다.");
            }
        }

    }
}
