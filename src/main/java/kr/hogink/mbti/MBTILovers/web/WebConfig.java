package kr.hogink.mbti.MBTILovers.web;

import kr.hogink.mbti.MBTILovers.web.login.AuthInterceptor;
import kr.hogink.mbti.MBTILovers.web.login.LoginInterceptor;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {
    private final MemberService memberService;

    public WebConfig(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        // 인증 인터셉터
        // 로그인된 유저인지 검증
        // 경로는 "/경로" 여야함
        registry.addInterceptor(new AuthInterceptor(memberService))
                .addPathPatterns("/*").excludePathPatterns("/upload").excludePathPatterns("/uploadProfileImage").excludePathPatterns("/base64Upload");


        //로그인 인터셉터
        //로그인을 처리함
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/user/loginPost");


    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/", "classpath:/templates/"
    };
}
