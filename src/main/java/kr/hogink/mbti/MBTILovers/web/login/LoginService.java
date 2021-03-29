package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;

public interface LoginService {

    Member login(LoginVO loginVO);
}
