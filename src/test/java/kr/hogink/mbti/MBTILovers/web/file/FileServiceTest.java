package kr.hogink.mbti.MBTILovers.web.file;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Transactional
class FileServiceTest {

    FileService fileService = new FileService();
    File localFile;

    @AfterEach
    public void afterEach() {

        if (localFile.exists())
            localFile.delete();
    }

    @Test
    @DisplayName("이름 없이 파일 업로드")
    void file_upload() {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());

        //when
        Path path = fileService.fileUpload(file, null);

        //then
        localFile = new File(path.toString());
        assertThat(localFile.getName()).isEqualTo("test.txt");
    }

    @Test
    @DisplayName("이름 변경해서 파일 업로드")
    void file_upload_with_name() {

        //given
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "hello file".getBytes());

        //when
        String filename = "파일명";
        Path path = fileService.fileUpload(file, filename);

        //then
        localFile = new File(path.toString());
        assertThat(localFile.getName()).isEqualTo(filename + ".txt");
    }

}