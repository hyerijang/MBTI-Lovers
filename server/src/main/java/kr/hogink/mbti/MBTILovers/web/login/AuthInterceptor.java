package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class AuthInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);
    private final MemberService memberService;

    public AuthInterceptor(MemberService memberService) {
        this.memberService = memberService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        HttpSession session = request.getSession();

        String newUserUid = (String) session.getAttribute(LoginType.NEW_USER_UID_SESSION);
        if (newUserUid != null) {
            Optional<Member> member = memberService.findOneByUid(newUserUid);

            if (member.isPresent()) {
                session.setAttribute(LoginType.USER_MEMBER_SESSION, member.get());
                session.removeAttribute(LoginType.NEW_USER_UID_SESSION);
                //쿠키 생성
                CookieManager.makeCookie(response, newUserUid);
            }

        }

        if (session.getAttribute(LoginType.USER_MEMBER_SESSION) == null) {
            // 현재 페이지 저장
            saveDestination(request);

            // 쿠키 유무 확인
            Cookie loginCookie = WebUtils.getCookie(request, LoginType.USER_UID_COOKIE);
            if (loginCookie != null) {
                Optional<Member> member = memberService.findOneByUid(loginCookie.getValue());
                if (member.isPresent()) {
                    session.setAttribute(LoginType.USER_MEMBER_SESSION, member.get());
                    return true;
                } else {
                    //존재하지 않는 멤버
                    //로그인 쿠키 삭제
                    logger.info("존재하지 않는 멤버는 로그인 할 수 없습니다.");
                    loginCookie.setMaxAge(0);
                }
            }

            response.sendRedirect("/user/login");
            return false;
        }


        return true;
    }

    // 로그인 페이지 이동 전 현재 페이지를 저장합니다.
    private void saveDestination(HttpServletRequest request) {
        String uri = request.getRequestURI();   // 현재 페이지
        String query = request.getQueryString(); // 쿼리
        if (query == null || query.equals("null")) {
            query = "";
        } else {
            query = "?" + query;
        }
        // 현재 페이지 + get 파라미터 저장
        if (request.getMethod().equals("GET")) {
            logger.info("destination : " + (uri + query));
            request.getSession().setAttribute("destination", uri + query);
        }
    }


}
