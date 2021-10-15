package kr.hogink.mbti.MBTILovers.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Locale;
import java.util.TimeZone;

@EnableJpaAuditing
@SpringBootApplication
@PropertySources({
        @PropertySource("classpath:s3.properties"),
        @PropertySource("classpath:db.properties")
})

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
