package kr.hogink.mbti.MBTILovers.web.domain.member;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import kr.hogink.mbti.MBTILovers.web.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;
import org.n52.jackson.datatype.jts.GeometryDeserializer;
import org.n52.jackson.datatype.jts.GeometrySerializer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseTimeEntity {

    @Id
    private String uid; //firebase uid
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String gender;
    @Column(nullable = false)
    private int age;
    @Column(nullable = false)
    private String mbti;
    @Column
    private String stateMessage;
    @Column
    private String profileImage;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(contentUsing = GeometryDeserializer.class)
    @Column(name = "location")
    private Point location;

    @Builder
    public Member(String name, String gender, int age, String mbti) {
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.mbti = mbti;
    }
}
