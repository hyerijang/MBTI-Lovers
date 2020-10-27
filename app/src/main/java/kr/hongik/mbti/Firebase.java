package kr.hongik.mbti;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
//연동테스트용 
public class Firebase {

    public String userName;
    public String email;

    public Firebase() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Firebase(String userName, String email) {
        this.userName = userName;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}