package kr.hogink.mbti.MBTILovers.web.repository;

import kr.hogink.mbti.MBTILovers.web.domain.Member;

import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository{
    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public Optional<Member> findByID(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<Member> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
}
