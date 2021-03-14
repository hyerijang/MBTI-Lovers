package kr.hogink.mbti.MBTILovers.web.repository;

import kr.hogink.mbti.MBTILovers.web.domain.Member;

import java.util.List;
import java.util.Optional;


public interface MemberRepository {

    Member save(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();

}
