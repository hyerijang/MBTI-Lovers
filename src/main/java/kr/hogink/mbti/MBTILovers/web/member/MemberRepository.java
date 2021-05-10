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


    @Query(value = "select * from member where location is not null order by ST_Distance(location, POINT(49, 40))  limit ?1", nativeQuery=true)
    List<Member> findNearPoint(int number);

    @Query(value = "select * from member where location is not null order by ST_Distance(location, POINT(?1, ?2))  limit ?3", nativeQuery=true)
    List<Member> findNearPoint(double x, double y, int number);
}
