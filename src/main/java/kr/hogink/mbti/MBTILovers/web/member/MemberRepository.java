package kr.hogink.mbti.MBTILovers.web.member;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findByName(String name);
    List<Member> findAll();
    Optional<Member> findByUid(String uid);
    List<Member> findMemberByPositionXBetween(String Start ,String End);

    @Query("FROM Member where positionX between ?1 AND ?2 and positionY between ?3 AND ?4")
    List<Member> findNear( String StartX, String EndX ,String startY, String endY );


    @Query("FROM Member where positionX IS NOT NULL")
    List<Member> findNear( );

}
