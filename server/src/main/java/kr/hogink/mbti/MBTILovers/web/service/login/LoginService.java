package kr.hogink.mbti.MBTILovers.web.service.login;

import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.login.LoginDto;

import java.util.Optional;

public interface LoginService {

    Optional<Member> login(LoginDto loginDto);
}
