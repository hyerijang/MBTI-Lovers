package kr.hogink.mbti.MBTILovers.web.login;

// DTO : 화면에서 전달되는 데이터를 수정하는 용도 : 아이디, 비밀번호, 로그인 유지 여부
public class LoginDTO {

    private String uid; // 아이디
    private boolean useCookie; // 로그인 유지 여부

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public boolean isUseCookie() {
        return useCookie;
    }

    public void setUseCookie(boolean useCookie) {
        this.useCookie = useCookie;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "uid='" + uid + '\'' +
                ", useCookie=" + useCookie +
                '}';
    }
}
