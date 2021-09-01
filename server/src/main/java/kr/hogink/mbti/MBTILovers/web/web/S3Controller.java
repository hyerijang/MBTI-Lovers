package kr.hogink.mbti.MBTILovers.web.web;

import kr.hogink.mbti.MBTILovers.web.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Controller
@Log4j2
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping("/upload")
    @ResponseBody
    public String upload(@RequestParam("data") MultipartFile multipartFile) throws IOException {
        return s3Uploader.upload(multipartFile, "static");
    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file, @RequestParam("uid_imageUpload") String uid) throws IOException {


        log.info("--------------------------------------");
        log.info("uid :" + uid);
        log.info("upload profileImage file to s3");


        s3Uploader.uploadProfileImage(file, "image", uid);
        return "redirect:/";
    }

    @PostMapping("/base64Upload")
    public String uploadBase64(String base64, String uid) {

        log.info("--------------------------------------");
        log.info("base64파일을 png로 변환하여 업로드");
        File file = s3Uploader.convertBase64ToPng(base64, uid);
        log.info("uid :" + uid);
        s3Uploader.uploadProfileImage(file, "image", uid);
        return "redirect:/";

    }


}

