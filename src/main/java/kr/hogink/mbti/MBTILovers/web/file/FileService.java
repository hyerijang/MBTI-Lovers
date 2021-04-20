package kr.hogink.mbti.MBTILovers.web.file;

import lombok.extern.log4j.Log4j2;
import org.springframework.jdbc.support.CustomSQLErrorCodesTranslation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.regex.Matcher;

@Service
@Log4j2
public class FileService {

    //    File file = new File("");
//    private final String projectPath = file.getAbsolutePath();
    private final String projectPath = System.getProperty("user.dir");

    private final String additionalPath = "/src/main/resources/static/images";
    private final String uploadDir = projectPath + additionalPath.replaceAll("/", Matcher.quoteReplacement(File.separator));

    public void fileUpload(MultipartFile multipartFile) {

        // File.seperator 는 OS종속적이다.
        // Spring에서 제공하는 cleanPath()를 통해서 ../ 내부 점들에 대해서 사용을 억제한다
        Path copyOfLocation = Paths.get(uploadDir + File.separator + StringUtils.cleanPath(multipartFile.getOriginalFilename()));
        log.info("저장될 파일 경로  : " + copyOfLocation.toString());
        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file : " + multipartFile.getOriginalFilename());
        }

    }
}