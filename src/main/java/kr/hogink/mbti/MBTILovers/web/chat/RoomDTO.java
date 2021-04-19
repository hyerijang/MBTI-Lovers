package kr.hogink.mbti.MBTILovers.web.chat;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
public class RoomDTO {

    private Long rid;
    private String fid;
    
}

