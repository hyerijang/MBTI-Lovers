package kr.hogink.mbti.MBTILovers.web.firestore;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.Charset;

@RestController
@RequestMapping(value = "/login")
public class LoginController {

    private final MemberService memberService;

    @Autowired
    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }


    /**
     * 응답 전송 : 로그인 인증
     **/
    @PostMapping(value = "/auth/{uid}")
    public Member findById(@PathVariable String uid) {
        Member mem = memberService.findOneByUid(uid).get();
        return mem;
    }



}
