package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.member.DBMemberRepository;
import kr.hogink.mbti.MBTILovers.web.member.MemberRepository;
import kr.hogink.mbti.MBTILovers.web.member.MemberService;
import kr.hogink.mbti.MBTILovers.web.member.MemberServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
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
        return new DBMemberRepository(dataSource);
    }

}
