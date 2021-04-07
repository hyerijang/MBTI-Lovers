package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//HttpSession 관련 작업 처리
public class LoginInterceptor extends HandlerInterceptorAdapter {



    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);

    // 로그인 처리 후 session 정보 보관
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        // session 값
        HttpSession session = request.getSession();
        // model에 저장된 값을 member에 저장
        ModelMap modelMap = modelAndView.getModelMap();
        Member member = (Member) modelMap.get("currentUser");

        // System.out.println(member+"님이 로그인 하셨습니다");
        if (member != null) {
            logger.info("new login success");
            // session에 로그인한 사용자의 멤버 객체를 저장
            session.setAttribute(LoginController.USER, member);
            //쿠키 생성
            if (true) {
                logger.info("make loginCookie");
                // 로그인 쿠키 객체 생성
                Cookie loginCookie = new Cookie("currentUserUid", member.getUid());
                // 모든 경로에서 접근 가능하게 처리
                loginCookie.setPath("/");
                // 쿠키 유효 기간
                loginCookie.setMaxAge(60 * 60 * 24 * 7);
                // 쿠키 저장!
                response.addCookie(loginCookie);
            }
            // 로그인 페이지 접근 전의 페이지
            Object destination = session.getAttribute("destination");
            // 삼항 연산자로 이전페이지가 존재하지 않으면 메인페이지로 이동
            response.sendRedirect(destination != null ? (String) destination : "/");
        }
    }

    // 기존 로그인 정보 있을 경우 session 정보 초기화
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        // session 값
        HttpSession session = request.getSession();
        // 기존 session login 값이 존재하면
        if (session.getAttribute(LoginController.USER) != null) {

            logger.info("clear login data before");
            // 삭제
            session.removeAttribute(LoginController.USER);
        }
        return true;
    }

}
