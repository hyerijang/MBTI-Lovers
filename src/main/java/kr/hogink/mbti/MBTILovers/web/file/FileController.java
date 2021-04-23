package kr.hogink.mbti.MBTILovers.web.file;

import kr.hogink.mbti.MBTILovers.web.login.LoginService;
import kr.hogink.mbti.MBTILovers.web.login.LoginType;
import kr.hogink.mbti.MBTILovers.web.member.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@SessionAttributes({LoginType.USER_MEMBER_SESSION, LoginType.NEW_USER_UID_SESSION})
@Controller
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    FileService fileService;
    LoginService loginService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //파일 업로드 테스트
    @GetMapping("/file")
    public String file() {
        return "fileUploadTest";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        logger.info("--------------------------------------");
        logger.info("Upload File Name : " + file.getOriginalFilename());
        logger.info("Upload File Size : " + file.getSize());
        fileService.fileUpload(file);
        return "redirect:/";

    }

    @PostMapping("/uploadProfileImage")
    public String uploadProfileImage(@RequestParam("file") MultipartFile file, @ModelAttribute(LoginType.USER_MEMBER_SESSION) Member user, @ModelAttribute(LoginType.USER_MEMBER_SESSION) Member new_user) {
        logger.info("--------------------------------------");
        logger.info("Upload File Name : " + file.getOriginalFilename());
        logger.info("Upload File Size : " + file.getSize());
        String uid;
        if (user == null) {
            uid = new_user.getUid();
        } else
            uid = user.getUid();

        fileService.fileUpload(file, uid);
        return "redirect:/";

    }


}
