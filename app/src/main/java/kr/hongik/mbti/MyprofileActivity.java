package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MyprofileActivity extends AppCompatActivity {

    private Button btn_update;

    private static final String TAG = "MyprofileActivity";
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference usersRef = db.collection("users").document(user.getUid());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);

        TextView my_nickname = (TextView)findViewById(R.id.my_nickname);
        TextView my_gender = (TextView)findViewById(R.id.my_gender);
        TextView my_age = (TextView)findViewById(R.id.my_age);
        TextView my_mbti = (TextView)findViewById(R.id.my_mbti);
        TextView my_address = (TextView)findViewById(R.id.my_address);
        TextView my_stateMessage = (TextView)findViewById(R.id.my_stateMessage);
        ImageView my_profile = findViewById(R.id.my_profile);
        btn_update = findViewById(R.id.btn_update);

        //프로필 이미지
        ProfileImage profileImage = new ProfileImage(getCacheDir());
        profileImage.showProfileImage(my_profile);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myStartActivity(UpdateActivity.class);
                finish();
            }
        });

        usersRef.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                my_nickname.setText("닉네임 : " + document.getString("nickname"));
                                my_gender.setText("성별 : " + document.getString("gender"));
                                my_age.setText("나이 : " + document.getString("age"));
                                my_mbti.setText("mbti : " + document.getString("mbti"));
                                my_address.setText("주소 : " + document.getString("address"));
                                my_stateMessage.setText("상태메시지 : " + document.getString("stateMessage"));

                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}