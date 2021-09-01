package kr.hogink.mbti.MBTILovers.web.web;

import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.login.LoginDto;
import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import kr.hogink.mbti.MBTILovers.web.service.login.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class LoginController {

    private final MemberService memberService;
    private final LoginService loginService;

    //로그인 페이지
    @GetMapping("/login")
    public String loginGET(LoginDto loginDto) {
        return "user/login";
    }

    //로그인 처리
    @PostMapping("/loginPost")
    public void loginPost(LoginDto loginDto, HttpSession session, Model model) {
        //신규 유저인 경우 loginVO에 신규유저 uid 넣음
        String newUseruid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        if (newUseruid != null)
            loginDto.setUid(newUseruid);

        Optional<Member> member = loginService.login(loginDto);

        if (member.isPresent()) {
            //model에 멤버 객체를 currentUser라는 이름의 변수에 저장
            model.addAttribute(LoginType.USER_MEMBER_SESSION, member.get());
        } else {
            //신규 가입
            session.setAttribute(LoginType.NEW_USER_UID_SESSION, loginDto.getUid());
        }

    }


    // 로그아웃 처리
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response,
                         HttpSession session) throws Exception {
        // session에 저장된 login 값
        Object object = session.getAttribute(LoginType.USER_MEMBER_SESSION);
        if (object != null) {
            Member userVO = (Member) object;
            // session 정보 삭제
            session.removeAttribute(LoginType.USER_MEMBER_SESSION);
            // session 초기화
            session.invalidate();
            // 로그인 쿠키값
            Cookie loginCookie = WebUtils.getCookie(request, LoginType.USER_UID_COOKIE);
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
