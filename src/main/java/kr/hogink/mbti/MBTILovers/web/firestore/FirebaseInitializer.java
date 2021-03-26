package kr.hogink.mbti.MBTILovers.web.firestore;

import java.io.IOException;

public class FirebaseInitializer {

    public static String uid = "1laInCxF3bMY2dHqx7eap8aOSo22";
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
