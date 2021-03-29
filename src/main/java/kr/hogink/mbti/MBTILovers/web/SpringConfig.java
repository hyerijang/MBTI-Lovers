package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.chatRoom.ChatRoomRepository;
import kr.hogink.mbti.MBTILovers.web.chatRoom.ChatRoomService;
import kr.hogink.mbti.MBTILovers.web.chatRoom.MemoryChatRoomRepository;
import kr.hogink.mbti.MBTILovers.web.login.AuthInterceptor;
import kr.hogink.mbti.MBTILovers.web.login.LoginInterceptor;
import kr.hogink.mbti.MBTILovers.web.login.LoginService;
import kr.hogink.mbti.MBTILovers.web.login.LoginServiceImpl;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepositoryImpl;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import kr.hogink.mbti.MBTILovers.web.member.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.DataSource;

@Configuration
public class SpringConfig extends WebMvcConfigurationSupport {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryImpl(dataSource);
    }

    @Bean
    public ChatRoomService chatRoomService() {
        return new ChatRoomService(chatRoomRepository());
    }

    @Bean
    public ChatRoomRepository chatRoomRepository()
    {
        return new MemoryChatRoomRepository();
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
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/chat/**")
                .addPathPatterns("/members");

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
