package kr.hogink.mbti.MBTILovers.web.member;

import lombok.*;
import org.locationtech.jts.geom.Point;

import javax.persistence.Column;
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
    @Column(name = "position_X")
    private String positionX;
    @Column(name = "position_Y")
    private String  positionY;

    private Point point;


}
