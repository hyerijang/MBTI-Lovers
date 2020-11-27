package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextView tv_uid, my_mbti;
    private Button btn_logout2, btn_searching, btn_userdata, btn_matching;
    FirebaseAuth mfirebaseAuth;
    FirebaseUser currentUser;
    private static final String TAG = "MainActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference usersRef = db.collection("users").document(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        mfirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mfirebaseAuth.getCurrentUser();
        tv_uid = findViewById(R.id.tv_firebase_uid);
        String userNum = currentUser.getUid();
        tv_uid.setText(userNum);

        my_mbti = findViewById(R.id.mymbti);

        usersRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                               if (task.isSuccessful()) {
                                                   DocumentSnapshot document = task.getResult();
                                                   if (document.exists()) {
                                                       Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                                       my_mbti.setText("나의 MBTI는 " + document.getString("mbti") + "입니다");

                                                   } else {
                                                       Log.d(TAG, "No such document");
                                                   }
                                               } else {
                                                   Log.d(TAG, "get failed with ", task.getException());
                                               }
                                           }
                                       });

        btn_logout2 = findViewById(R.id.btn_logout2);

        btn_logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(mfirebaseAuth);
                myStartActivity(LoginActivity.class);
                finish();
            }
        });

        btn_matching = findViewById(R.id.btn_matching);

        btn_matching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStartActivity(MatchingActivity.class);
                finish();
            }
        });

        btn_searching = findViewById(R.id.btn_searching);

        btn_searching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStartActivity(SearchingActivity.class);
                finish();
            }
        });

        btn_userdata = findViewById(R.id.btn_userdata);

        btn_userdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStartActivity(MyprofileActivity.class);
                finish();
            }
        });

        }

        private void init(){
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            if (firebaseUser == null) {
                myStartActivity(LoginActivity.class);
            }
            else{
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                DocumentReference documentReference = db.collection("users").document(firebaseUser.getUid());
                documentReference.get().addOnCompleteListener((task) -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null) {
                                if (document.exists()) {
                                    Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                } else {
                                    Log.d(TAG, "No such document");
                                    myStartActivity(JoinActivity.class);

                                }
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                );
            }
        }



    public void logout(FirebaseAuth mFirebaseAuth) {

        if (mFirebaseAuth.getCurrentUser() != null) {

            Toast.makeText(MainActivity.this, mFirebaseAuth.getUid() + "님이 로그아웃하셨습니다", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signOut();

        }
    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

}