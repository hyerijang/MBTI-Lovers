package kr.hongik.mbti;

public class MemberInfo {
    private String nickname;
    private String age;
    private String address;
    private String mbti;
    private String stateMessage;

    public MemberInfo(String nickname, String age, String address, String mbti, String stateMessage){
        this.nickname=nickname;
        this.age=age;
        this.address=address;
        this.mbti=mbti;
        this.stateMessage=stateMessage;
    }

    public String getNickname(){
        return this.nickname;
    }
    public void setNickname(){
        this.nickname=nickname;
    }
    public String getAge(){
        return this.age;
    }
    public void setAge(){
        this.age=age;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(){
        this.address=address;
    }
    public String getMbti(){
        return this.mbti;
    }
    public void setMbti(){
        this.mbti=mbti;
    }
    public String getStateMessage(){
        return this.stateMessage;
    }
    public void setStateMessage(){
        this.stateMessage=stateMessage;
    }
}
