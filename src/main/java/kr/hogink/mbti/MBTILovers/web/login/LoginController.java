package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

//linux 서버에서 500 오류 안나려면
//@RequestMapping 리턴시  "user/login" 로 해야함
//"/user/loing"으로하면 오류


@Controller
@RequestMapping("/user")
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;
    public static final String USER_SESSION = "currentUser";
    public static final String USER_COOKIE = "currentUserUid";
    public static final String NewUserUid = "newUser";

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
    public void loginPost(LoginVO loginVO, HttpSession session, Model model) {
        //신규 유저인 경우 loginVO에 신규유저 uid 넣음
        String newUseruid = (String) session.getAttribute(NewUserUid);
        if (newUseruid != null)
            loginVO.setUid(newUseruid);

        Optional<Member> member = loginService.login(loginVO);

        if (member.isPresent()) {
            //기존 유저면 접속시간 갱신
            memberService.editLastConnectTime(member.get());
            //model에 멤버 객체를 currentUser라는 이름의 변수에 저장
            model.addAttribute("currentUser", member.get());
        } else {
            //신규 가입
            session.setAttribute(NewUserUid, loginVO.getUid());
        }

    }


    // 로그아웃 처리
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session) throws Exception {
        // session에 저장된 login 값
        Object object = session.getAttribute(USER_SESSION);
        if (object != null) {
            Member userVO = (Member) object;
            // session 정보 삭제
            session.removeAttribute(USER_SESSION);
            // session 초기화
            session.invalidate();
            // 로그인 쿠키값
            Cookie loginCookie = WebUtils.getCookie(request, USER_COOKIE);
            if (loginCookie != null) {
                loginCookie.setPath("/");
                // 쿠키 유효기간 0
                loginCookie.setMaxAge(0);
                // 쿠키 저장
                response.addCookie(loginCookie);
            }
        }

        return "redirect:/";
    }

}
