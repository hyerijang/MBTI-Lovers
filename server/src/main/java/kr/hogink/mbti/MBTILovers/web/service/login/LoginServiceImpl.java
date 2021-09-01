package kr.hogink.mbti.MBTILovers.web.service.login;

import kr.hogink.mbti.MBTILovers.web.login.LoginVO;
import kr.hogink.mbti.MBTILovers.web.domain.member.Member;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final MemberService memberService;

    public LoginServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Optional<Member> login(LoginVO loginVO) {
        return memberService.findOneByUid(loginVO.getUid());
    }
}
