package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.member.Member;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    Optional<Member> findByUid(String uid);

//userDAO

    // 회원정보 수정처리
    void updateUser(Member member);

    // 로그인 유지
    void keepLogin(String uid, String sessionId, Date next) throws Exception;

    // sessionKey 확인
    Member checkUserWithSessionKey(String value) throws Exception;

}
