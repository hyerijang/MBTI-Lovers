package kr.hogink.mbti.MBTILovers.web.file;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    FileService fileService;

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

}
