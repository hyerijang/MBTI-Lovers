package kr.hogink.mbti.MBTILovers.web.service;


import com.google.api.client.util.Value;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import kr.hogink.mbti.MBTILovers.web.member.MemberServiceImpl;
import kr.hogink.mbti.MBTILovers.web.member.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService =  new MemberServiceImpl(memberRepository);
    }
    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }
    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");
        //when
        Long saveId = memberService.join(member);
        //then
        //assertj의 assertions
        Member findMember = memberService.findOneById(saveId).get();
        assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");
        Member member2 = new Member();
        member2.setName("spring");
        //when
        memberService.join(member1);
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        //then
    }




    @Test
    public void 경로검증() throws URISyntaxException {

        String path = File.separator + "static" +File.separator +"images" + File.separator;
        String fileName = "defaultProfileImage.png";
        URL res = getClass().getClassLoader().getResource(path + fileName);
        File file = Paths.get(res.toURI()).toFile();
        System.out.println("file is exist ? " + file.exists());
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
