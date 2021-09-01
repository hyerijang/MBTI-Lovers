package kr.hogink.mbti.MBTILovers.web.service.login;

import kr.hogink.mbti.MBTILovers.web.login.LoginVO;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(LoginVO loginVO);
}
