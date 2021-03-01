package kr.hogink.mbti.MBTILovers.web;


import kr.hogink.mbti.MBTILovers.web.repository.JdbcMemberRepository;
import kr.hogink.mbti.MBTILovers.web.repository.JdbcTemplateMemberRepository;
import kr.hogink.mbti.MBTILovers.web.repository.MemberRepository;
import kr.hogink.mbti.MBTILovers.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {

    private  DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository() {
// return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);
        return new JdbcTemplateMemberRepository(dataSource);
    }
}
