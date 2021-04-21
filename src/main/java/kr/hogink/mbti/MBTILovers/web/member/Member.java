package kr.hogink.mbti.MBTILovers.web.member;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Member {
    //    private static final long serialVersionUID = 1L;
    @Id
    @NonNull
    private String uid; //firebase uid
    private String name;
    private String gender;
    private int age;
    private String mbti;
    private String stateMessage;
    private String profileImage;
    private LocalDateTime connectedTimeAt;


}
