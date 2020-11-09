package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import kr.hongik.mbti.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tv_uid;
    private Button btn_logout2;

    FirebaseAuth mfirebaseAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mfirebaseAuth = FirebaseAuth.getInstance();
        currentUser = mfirebaseAuth.getCurrentUser();
        tv_uid = findViewById(R.id.tv_firebase_uid);
        String userNum = currentUser.getUid();
        tv_uid.setText(userNum);

        btn_logout2 = findViewById(R.id.btn_logout2);

        btn_logout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(mfirebaseAuth);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }



    public void logout(FirebaseAuth mFirebaseAuth) {

        if (mFirebaseAuth.getCurrentUser() != null) {

            Toast.makeText(MainActivity.this,   mFirebaseAuth.getUid()+"님이 로그아웃하셨습니다", Toast.LENGTH_SHORT).show();
            mFirebaseAuth.signOut();
        }

    }

}