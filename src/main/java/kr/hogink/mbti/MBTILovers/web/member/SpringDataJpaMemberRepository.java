package kr.hogink.mbti.MBTILovers.web.member;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, String>, MemberRepository {

    @Override
    Optional<Member> findByName(String name);


}
