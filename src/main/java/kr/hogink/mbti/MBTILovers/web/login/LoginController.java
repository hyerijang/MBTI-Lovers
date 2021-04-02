package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

//linux 서버에서 500 오류 안나려면
//@RequestMapping 리턴시  "user/login" 로 해야함
//"/user/loing"으로하면 오류


@Controller
@RequestMapping( "/user")
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;
    public static final String USER = "user";

    public LoginController(MemberService memberService, LoginService loginService) {
        this.memberService = memberService;
        this.loginService = loginService;
    }


    //로그인 페이지
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginGET(LoginVO loginVO) {
        return "user/login";
    }

    //로그인 처리
    @RequestMapping(value = "/loginPost", method = RequestMethod.POST)
    public void loginPost(LoginVO loginVO, HttpSession httpSession, Model model) {
        //uid를 통해 select한 회원 정보를 member에 담는다.
        Member member = loginService.login(loginVO);
        memberService.editLastConnectTime(member);
        if(member ==null)
        {
            return;
        }

        //model에 멤버 객체를 currentUser라는 이름의 변수에 저장
        model.addAttribute("currentUser", member);
    }



}
