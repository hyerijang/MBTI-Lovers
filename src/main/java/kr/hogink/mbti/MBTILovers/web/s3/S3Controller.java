package kr.hogink.mbti.MBTILovers.web.s3;

import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
@Log4j2
public class S3Controller {
    private final S3Uploader s3Uploader;

    @GetMapping("/test")
    public String index() {
        return "test";
    }

    @PostMapping("/uploadS3")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static");
    }

    @PostMapping("/uploadS3ProfileImage")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("uid2") String uid) throws IOException {


        log.info("--------------------------------------");
        log.info("uid :" +uid);
        log.info("upload profileImage file to s3");
        return s3Uploader.uploadProfileImage(file, "image", uid);
    }

}

