package kr.hogink.mbti.MBTILovers.web.domain.member;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.hogink.mbti.MBTILovers.web.domain.BaseTimeEntity;
import lombok.*;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
public class Member extends BaseTimeEntity {
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
    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    @Column(name = "location")
    private Point location;

}
