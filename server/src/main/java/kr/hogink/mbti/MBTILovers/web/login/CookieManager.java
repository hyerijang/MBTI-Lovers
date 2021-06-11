package kr.hogink.mbti.MBTILovers.web.login;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import static kr.hogink.mbti.MBTILovers.web.login.LoginType.USER_UID_COOKIE;

@Log4j2
public class CookieManager {

    public static void makeCookie(HttpServletResponse response , String uid)
    {
        log.info("make loginCookie for new user");
        // 로그인 쿠키 객체 생성
        Cookie loginCookie = new Cookie(USER_UID_COOKIE, uid);
        // 모든 경로에서 접근 가능하게 처리
        loginCookie.setPath("/");
        // 쿠키 유효 기간
        loginCookie.setMaxAge(60 * 60 * 24 * 7);
        // 쿠키 저장!
        response.addCookie(loginCookie);
    }
}
