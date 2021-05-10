package kr.hogink.mbti.MBTILovers.web.member;

import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        //UID 검증
        validateUid(member);
        //같은 UID인 중복회원 x
        validateDuplicateMember(member);
        //프로필 이미지로 기본이미지 세팅
        setDefaultProfileImage(member);
        //현재시각 저장
        setLastConnectTime(member);
        memberRepository.save(member);
        return member.getUid();
    }

    @Override
    public String edit(Member member) {
        //UID 검증
        validateUid(member);
        //같은 UID인 중복회원 x
//        validateDuplicateMember(member);
        //프로필 이미지로 기본이미지 세팅
        setDefaultProfileImage(member);
        //현재시각 저장
        setLastConnectTime(member);
        memberRepository.save(member);
        return member.getUid();
    }

    private void validateUid(Member member) {
        //uid가 비어있으면 임시 uid 발급
        if (member.getUid() == null) {
//            member.setUid(UUID.randomUUID().toString());
            throw new IllegalStateException("member uid가 null 입니다.");
        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUid(member.getUid()).
                ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }


    private void setDefaultProfileImage(Member member) {

        String defaultFileName = "defaultProfileImage.png";
        if (member.getProfileImage() == null)
            member.setProfileImage(defaultFileName);

    }

    public void setLastConnectTime(Member member) {
        member.setConnectedTimeAt(LocalDateTime.now());
    }

    public void renewLastConnectTime(Member member) {
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


    /**
     * 전체 회원 조회
     */
    @Override
    public List<Member> findNearMembers(String X, String Y) {

        int radius = 10;
        String StartX = Integer.toString(Integer.parseInt(X) - radius);
        String StartY = Integer.toString(Integer.parseInt(Y) - radius);
        String EndX = Integer.toString(Integer.parseInt(X) + radius);
        String EndY = Integer.toString(Integer.parseInt(Y) + radius);

        return memberRepository.findNear(StartX, EndX, StartY, EndY);
    }

    @Override
    public List<Member> findNearMembers(String StartX, String EndX, String startY, String endY) {
        return memberRepository.findNear(StartX, EndX, startY, endY);

    }

    @Override
    public List<Member> findNearMembers() {
        return memberRepository.findNear();
    }

    @Override
    public List<Member> findNearUser(double x, double y, int number) {
        List<Member> nearPoint = memberRepository.findNearPoint(x,y,number);
        System.out.println("######################################################################");
        for (int i = 0; i < nearPoint.size(); i++) {
            Member member = nearPoint.get(i);
            System.out.println(member.getUid());
        }
        System.out.println("######################################################################");
        return nearPoint;
    }
}
