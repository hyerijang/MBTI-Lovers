package kr.hogink.mbti.MBTILovers.web.firebase;



import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class FirebaseAuthentication {

    private final FirebaseAuth mFirebaseAuth;
    private UserRecord userRecord;
    /**
     * 파이어베이스 인증 초기화
     * crruentDirFile : 프로젝트 절대 경로,경로 구분 시 File.separator 사용 할 것
     */

    File currentDirFile = new File(".");
    String helper = currentDirFile.getAbsolutePath();
    String path =  helper + File.separator+ "firebase" + File.separator + "mbti-lovers-4b1ae-firebase-adminsdk-zvjex-c80f3735b9.json";

    public FirebaseAuthentication() throws IOException {
        // [START fs_initialize]
        // [START firestore_setup_client_create]
        InputStream serviceAccount = new FileInputStream(path);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);

        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        mFirebaseAuth = FirebaseAuth.getInstance();
    }
//
//    public UserRecord setUserRecord(String uid){
//        try {
//            this.userRecord = mFirebaseAuth.getUser(uid);
//            return this.userRecord ;
//        } catch (FirebaseAuthException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public UserRecord getUserRecord() {
//        return userRecord;
//    }
}