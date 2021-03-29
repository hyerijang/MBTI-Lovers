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

/**
 * A simple Quick start application demonstrating how to connect to Firestore
 * and add and query documents.
 */
public class FirebaseAuthentication {

    private final FirebaseAuth mFirebaseAuth;
    private UserRecord userRecord;
    /**
     * Initialize Firestore using default project ID.
     */

    //프로젝트 절대 경로 (윈도우와 리눅스 서로 다름 주의)
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

    public UserRecord setUserRecord(String uid){
        try {
            this.userRecord = mFirebaseAuth.getUser(uid);
            return this.userRecord ;
        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserRecord getUserRecord() {
        return userRecord;
    }
}