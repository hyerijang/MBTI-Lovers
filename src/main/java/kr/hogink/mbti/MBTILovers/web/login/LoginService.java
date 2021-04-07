package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(LoginVO loginVO);
}
