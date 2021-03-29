package kr.hogink.mbti.MBTILovers.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MbtiLoversWebApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(MbtiLoversWebApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(MbtiLoversWebApplication.class, args);
    }

}
