package kr.hogink.mbti.MBTILovers.web.file;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static kr.hogink.mbti.MBTILovers.web.file.FilePath.imagesPath;

@Service
@Log4j2
public class FileService {

    public Path fileUpload(MultipartFile multipartFile) {
        return fileUpload(multipartFile, null);
    }

    public Path fileUpload(MultipartFile multipartFile, String filename) {
        String originalFilename = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Path copyOfLocation = setFilePath(originalFilename, filename);

        log.info("저장될 파일 경로  : " + copyOfLocation.toString());
        try {
            // inputStream을 가져와서
            // copyOfLocation (저장위치)로 파일을 쓴다.
            Files.copy(multipartFile.getInputStream(), copyOfLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
            throw new FileStorageException("Could not store file : " + multipartFile.getOriginalFilename());
        }

        return copyOfLocation;
    }

    public Path setFilePath(String oldFilename, String newFilename) {
        String filename;
        //파일 이름을 명시하지 않은 경우 기존 파일 이름으로 설정
        if (newFilename == null) {
            filename = oldFilename; //기존 파일 이름
        } else {
            filename = newFilename + oldFilename.substring(oldFilename.lastIndexOf(".")); //새로운 파일이름 + 기존 파일 확장자
        }
        return Paths.get(imagesPath + File.separator + filename);
    }

}