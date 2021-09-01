package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//HttpSession 관련 작업 처리
public class LoginInterceptor extends HandlerInterceptorAdapter {


    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

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
        Member member = (Member) modelMap.get(LoginType.USER_MEMBER_SESSION);

        if (member != null) {
            logger.info("login success");
            // session에 로그인한 사용자의 멤버 객체를 저장
            session.setAttribute(LoginType.USER_MEMBER_SESSION, member);
            //쿠키 생성
            CookieManager.makeCookie(response, member.getUid());
            // 로그인 페이지 접근 전의 페이지
            Object destination = session.getAttribute("destination");
            // 삼항 연산자로 이전페이지가 존재하지 않으면 메인페이지로 이동
            response.sendRedirect(destination != null ? (String) destination : "/");
        } else {
            //신규유저 가입하러 이동
            logger.info("신규유저가입 페이지로 이동");
            response.sendRedirect("/members/new");
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

        if (session.getAttribute(LoginType.USER_MEMBER_SESSION) != null) {

            logger.info("clear login data before");
            // 삭제
            session.removeAttribute(LoginType.USER_MEMBER_SESSION);
        }
        return true;
    }

}
