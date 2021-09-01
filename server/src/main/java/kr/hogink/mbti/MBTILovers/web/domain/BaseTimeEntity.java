package kr.hogink.mbti.MBTILovers.web.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass //jpa Entity  클래스들이 BaseTimeEntity 클래스를 상속할 경우 필드들도 칼럼으로 인식하도록 한다.
@EntityListeners(AuditingEntityListener.class) // BaseTimeEntity 클래스에 Auditing 기능 포함
public abstract class BaseTimeEntity {

    @CreatedDate //Entity가 생성될 때 자동으로 시간 저장
    private LocalDateTime createdDate;

    @LastModifiedDate //Entity가 수정될 때 자동으로 시간 저장
    private LocalDateTime modifiedDate;

}
