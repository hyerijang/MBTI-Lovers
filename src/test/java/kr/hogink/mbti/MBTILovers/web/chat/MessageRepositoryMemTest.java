package kr.hogink.mbti.MBTILovers.web.chat;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MessageRepositoryMemTest {

    MessageRepositoryMem messageRepositoryMem;

    @AfterEach
    public void afterEach(){
        messageRepositoryMem.clear();
    }
    @BeforeEach
    public void beforeEach() {
        messageRepositoryMem = new MessageRepositoryMem();
    }

    @Test
    public void 메세지_저장(){
        Message message = new Message();
        message.setMsgId(1L);
        message.setContent("json 파일");
        messageRepositoryMem.save(message);

        Message findMessage = messageRepositoryMem.findByMsgId(1L);
//        findMessage.setContent("수정했습니다");
//        System.out.println("메세지 내용: " + message.getContent());
        assertThat(message.getContent()).isEqualTo(findMessage.getContent());

    }



}