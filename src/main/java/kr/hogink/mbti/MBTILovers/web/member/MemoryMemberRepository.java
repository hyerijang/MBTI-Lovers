package kr.hogink.mbti.MBTILovers.web.member;

import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {

    @Override
    public List<Member> findNear(String StartX, String EndX) {
        return null;
    }

    private static final Map<String, Member> store = new HashMap<>();
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        if (member.getUid() == null)
            member.setUid("UID" + (++sequence)); //임시
        store.put(member.getUid(), member);
        return member;
    }


    @Override
    public Optional<Member> findByUid(String uid) {
        return Optional.ofNullable(store.get(uid));
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

    @Override
    public List<Member> findMemberByPositionXBetween(String Start, String End) {
        return null;
    }
}
