package kr.hogink.mbti.MBTILovers.web.domain.member;

import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceIntegrationTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void 회원가입() {
        //given
        Member member = Member.builder()
                .name("김홍익")
                .age(12)
                .gender("남자")
                .mbti("ENFP")
                .build();
        String saveId = "testuser";
        member.setUid(saveId);

        //when
        memberService.join(member);
        //then
        //assertj의 assertions
        Member findMember = memberService.findOneByUid(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }


//    @Test
//    void 특정_좌표에_가까운_멤버_리스트() {
//
//        List<Member> nearPoint = memberRepository.findNearPoint(39.1, 127.2, 2);
//        System.out.println("######################################################################");
//
//        for (int i = 0; i < nearPoint.size(); i++) {
//            Member member = nearPoint.get(i);
//            System.out.println(member.getUid());
//
//        }
//    }

}
