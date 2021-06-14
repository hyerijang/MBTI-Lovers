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


    @Query(value = "select * from member where location is not null order by ST_Distance(location, POINT(?1, ?2))  limit ?3", nativeQuery = true)
    List<Member> findNearPoint(double latitude, double longitude, int n);


    @Query(value = "SELECT r.*  FROM (SELECT m.* \tFROM member m \tleft join friend f \ton f.uid = ?4 and m.uid = f.fid where f.uid is null or f.relation not in (\"FRIEND\",\"BLOCK\",\"BLOCKED\"))  as r where r.location is not null order by ST_Distance(location, POINT(?1, ?2))  limit ?3", nativeQuery = true)
    List<Member> findNearPointNotFriend(double latitude, double longitude, int n, String uid);

    void deleteById(String s);
}
