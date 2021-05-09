package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginVO;

import java.util.List;
import java.util.Optional;

public interface MemberService {

    String join(Member member);
    String edit(Member member);

    List<Member> findMembers();

    Optional<Member> findOneByUid(String memberUid);

    void renewLastConnectTime(Member member);

    List<Member> findNearMembers(int X, int Y);
}
