package kr.hogink.mbti.MBTILovers.web.member;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(Member member);
    List<Member> findMembers();
    Optional<Member> findOne(Long memberID);
}
