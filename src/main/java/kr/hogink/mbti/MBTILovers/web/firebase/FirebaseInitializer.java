package kr.hogink.mbti.MBTILovers.web.firebase;

import java.io.IOException;

public class FirebaseInitializer {

    /** firebase에 연결**/
    public  FirebaseAuthentication initFirebase(){

        try{
            return new FirebaseAuthentication();

        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }


}
