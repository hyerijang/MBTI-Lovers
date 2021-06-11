package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginVO;
import org.locationtech.jts.geom.Point;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    String join(Member member);

    String edit(Member member);

    List<Member> findMembers();

    Optional<Member> findOneByUid(String memberUid);

    void renewLastConnectTime(Member member);

    List<Member> findNearUser(double latitude, double longitude, int number);
    List<Member> findNearUserNotFriend(double latitude, double longitude, int number, String uid);

    void setPoint(Optional<Member> optMember, double latitude, double longitude);
}
