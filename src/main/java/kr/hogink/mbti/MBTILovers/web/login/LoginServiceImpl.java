package kr.hogink.mbti.MBTILovers.web.login;

import kr.hogink.mbti.MBTILovers.web.member.Member;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;

public class LoginServiceImpl implements LoginService {

    private final MemberService memberService;

    public LoginServiceImpl(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public Member login(LoginVO loginVO) {
        return memberService.findOneByUid(loginVO.getUid()).get();
    }
}
