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
    public void setNickname(String nickname){
        this.nickname=nickname;
    }
    public String getAge(){
        return this.age;
    }
    public void setAge(String age){
        this.age=age;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address=address;
    }
    public String getMbti(){
        return this.mbti;
    }
    public void setMbti(String mbti){
        this.mbti=mbti;
    }
    public String getStateMessage(){
        return this.stateMessage;
    }
    public void setStateMessage(String stateMessage){
        this.stateMessage=stateMessage;
    }
}
