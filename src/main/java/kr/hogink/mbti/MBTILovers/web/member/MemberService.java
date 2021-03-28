package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.login.LoginDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    Long join(Member member);
    List<Member> findMembers();
    Optional<Member> findOneById(Long memberID);
    Optional<Member> findOneByUid(String memberUid);


    void modifyUser(Member member);

    Member login (LoginDTO loginDTO);
    Optional<Member> checkLoginBefore(String value) throws Exception;
    Optional<Member> checkUserWithSessionKey(String value) throws Exception;
}
