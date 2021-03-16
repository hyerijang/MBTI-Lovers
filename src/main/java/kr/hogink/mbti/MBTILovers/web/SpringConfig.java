package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.repository.JdbcTemplateMemberRepository;
import kr.hogink.mbti.MBTILovers.web.repository.MemberRepository;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final DataSource dataSource;

    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new JdbcTemplateMemberRepository(dataSource);
    }
}