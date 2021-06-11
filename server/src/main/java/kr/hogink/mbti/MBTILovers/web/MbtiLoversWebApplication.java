package kr.hogink.mbti.MBTILovers.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
public class MbtiLoversWebApplication extends SpringBootServletInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(MbtiLoversWebApplication.class);
    }

    public static void main(String[] args) {
        
        //TimeZone설정
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        Locale.setDefault(Locale.KOREA);

        SpringApplication.run(MbtiLoversWebApplication.class, args);
    }

}
