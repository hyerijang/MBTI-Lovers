package kr.hogink.mbti.MBTILovers.web.member;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;


@Entity
public class Member {
    //    private static final long serialVersionUID = 1L;
    @Id
    private String uid; //firebase uid
    private String name;
    private String gender;
    private int age;
    private String mbti;
    private String stateMessage;
    private String profileImage;
    private LocalDateTime connectedTimeAt;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMbti() {
        return mbti;
    }

    public void setMbti(String mbti) {
        this.mbti = mbti;
    }

    public String getStateMessage() {
        return stateMessage;
    }

    public void setStateMessage(String stateMessage) {
        this.stateMessage = stateMessage;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public LocalDateTime getConnectedTimeAt() {
        return connectedTimeAt;
    }

    public void setConnectedTimeAt(LocalDateTime lastConnectTime) {
        this.connectedTimeAt = lastConnectTime;
    }
}
