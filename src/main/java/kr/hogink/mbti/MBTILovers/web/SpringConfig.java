package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.friend.FriendRepository;
import kr.hogink.mbti.MBTILovers.web.friend.FriendService;
import kr.hogink.mbti.MBTILovers.web.friend.FriendServiceImpl;
import kr.hogink.mbti.MBTILovers.web.login.AuthInterceptor;
import kr.hogink.mbti.MBTILovers.web.login.LoginInterceptor;
import kr.hogink.mbti.MBTILovers.web.login.LoginService;
import kr.hogink.mbti.MBTILovers.web.login.LoginServiceImpl;
import kr.hogink.mbti.MBTILovers.web.member.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class SpringConfig  {
    private final MemberRepository memberRepository;
    private final FriendRepository friendRepository;

    public SpringConfig(MemberRepository memberRepository, FriendRepository friendRepository) {
        this.memberRepository = memberRepository;
        this.friendRepository = friendRepository;
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository);
    }

    @Bean
    public FriendService friendService() {
        return new FriendServiceImpl(friendRepository);
    }

    @Bean
    public LoginService loginService() {
        return new LoginServiceImpl(memberService());
    }



}
