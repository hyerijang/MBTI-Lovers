package kr.hogink.mbti.MBTILovers.web.repository;

import kr.hogink.mbti.MBTILovers.web.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class MemoryMemberRepository implements MemberRepository{

    private static Map<String, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(String.valueOf(++sequence));
        store.put(member.getId(),member);
        return member;
    }


    @Override
    public Optional<Member> findById(String id) {
        return Optional.of(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream().filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }

}
