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
public class SpringConfig extends WebMvcConfigurationSupport {
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
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }


    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 인증 인터셉터
        // 로그인된 유저인지 검증
        // 경로는 "/경로" 여야함
        registry.addInterceptor(new AuthInterceptor(memberService()))
                .addPathPatterns("/*");

        //로그인 인터셉터
        //로그인을 처리함
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/loginPost");


    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/"
    };


    @Bean
    public LoginService loginService() {
        return new LoginServiceImpl(memberService());
    }
}
