package kr.hogink.mbti.MBTILovers.web.member;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    @Override
    public String join(Member member) {
        //uid가 비어있으면 임시 uid 발급
        if(member.getUid() == null)
            member.setUid(UUID.randomUUID().toString());
        //같은 이름이 있는 중복 회원x
        validateDuplicateMember(member);
        //프로필 이미지로 기본이미지 세팅
        setDefaultProfileImage(member);
        //현재시각 저장
        setLastConnectTime(member);
        memberRepository.save(member);
        return member.getUid();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).
                ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    private void setDefaultProfileImage(Member member) {

        String defaultFileName = "defaultProfileImage.png";
        member.setProfileImage(defaultFileName);

    }

    public void setLastConnectTime(Member member){
        member.setLastConnectTime(new Date(System.currentTimeMillis()));
    }
    public void editLastConnectTime(Member member) {
        setLastConnectTime(member);
        memberRepository.save(member);
    }

    /**
     * 전체 회원 조회
     */
    @Override
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }


    @Override
    public Optional<Member> findOneByUid(String memberUid) {
        return memberRepository.findByUid(memberUid);
    }


}
