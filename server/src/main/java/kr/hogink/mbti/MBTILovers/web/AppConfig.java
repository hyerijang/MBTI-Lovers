package kr.hogink.mbti.MBTILovers.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.aws.autoconfigure.context.ContextInstanceDataAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@EnableAutoConfiguration(exclude = {ContextInstanceDataAutoConfiguration.class})
@Configuration
@ComponentScan
public class AppConfig {
//의존관계 자동 주입
}