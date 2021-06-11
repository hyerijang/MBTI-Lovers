package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
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
