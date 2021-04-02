package kr.hogink.mbti.MBTILovers.web.friend;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Friend {

    @Id
    String uid;
    String fid; //friend's uid
    String relation;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String rid) {
        this.relation = rid;
    }
}
