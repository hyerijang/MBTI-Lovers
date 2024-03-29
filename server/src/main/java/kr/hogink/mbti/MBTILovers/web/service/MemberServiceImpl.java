package kr.hogink.mbti.MBTILovers.web.service;

import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.domain.member.MemberRepository;
import lombok.extern.log4j.Log4j2;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Service
@Log4j2
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
        memberRepository.save(member);
        return member.getUid();
    }

    @Override
    public String edit(Member member) {
        //UID 검증
        validateUid(member);
        return member.getUid();
    }

    private void validateUid(Member member) {
        //uid가 비어있으면 예외 던짐
        if (member.getUid() == null) {
            throw new IllegalStateException("member uid가 null 입니다.");
        }
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByUid(member.getUid()).
                ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
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


    @Override
    public List<Member> findNearUser(double latitude, double longitude, int number) {
        List<Member> nearPoint = memberRepository.findNearPoint(latitude, longitude, number);
        return nearPoint;
    }

    /**
     * 인근 유저를 찾아주는 함수입니다. 친구인 유저와 차단된 유저는 표시되지 않습니다.
     *
     * @param latitude
     * @param longitude
     * @param number
     * @param uid
     * @return
     */
    @Override
    public List<Member> findNearUserNotFriend(double latitude, double longitude, int number, String uid) {
        List<Member> nearPoint = memberRepository.findNearPointNotFriend(latitude, longitude, number, uid);
        System.out.println("######################################################################");
        System.out.println(latitude + " " + longitude + " " + number + " " + uid);

        for (int i = 0; i < nearPoint.size(); i++) {
            Member member = nearPoint.get(i);
            System.out.println(member.getUid());
        }
        System.out.println("######################################################################");
        return nearPoint;
    }

    /**
     * @param optMember 멤버
     * @param latitude  위도
     * @param longitude 경도
     */
    @Override
    public void setPoint(Optional<Member> optMember, double latitude, double longitude) {
        if (optMember.isPresent()) {
            Member member = optMember.get();

            //위치좌표 Point로 변환
            String pointWKT = String.format("POINT(%s %s)", latitude, longitude);
            try {
                Point point = (Point) new WKTReader().read(pointWKT);
                member.setLocation(point);
                edit(member);
                MemberServiceImpl.log.info(latitude + " " + longitude);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}
