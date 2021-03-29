package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginVO;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(Member member);

    List<Member> findMembers();

    Optional<Member> findOneById(Long memberID);

    Optional<Member> findOneByUid(String memberUid);

}
