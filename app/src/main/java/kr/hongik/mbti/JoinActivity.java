package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.FileObserver;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinActivity extends AppCompatActivity {

    private final int GET_GALLERY_IMAGE = 200;
    private ImageView imageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        TextInputLayout inputLayout = findViewById(R.id.nickname);
        TextInputLayout inputLayout1 = findViewById(R.id.age);
        TextInputLayout inputLayout2 = findViewById(R.id.address);

        inputLayout.setCounterEnabled(true);
        inputLayout1.setCounterEnabled(true);
        inputLayout2.setCounterEnabled(true);
        inputLayout.setCounterMaxLength(20);
        inputLayout1.setCounterMaxLength(3);
        inputLayout2.setCounterMaxLength(50);

        imageview = (ImageView) findViewById(R.id.image);
        imageview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        Button button = findViewById(R.id.JoinButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileUpdate();
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void profileUpdate() {
        String nickname = ((EditText) findViewById(R.id.nickname)).getText().toString();
        //String gender = ((EditText)findViewById(R.id.)).getText().toString();
        String age = ((EditText) findViewById(R.id.age)).getText().toString();
        String address = ((EditText) findViewById(R.id.address)).getText().toString();
        String mbti = ((EditText) findViewById(R.id.mbti)).getText().toString();
        String stateMessage = ((EditText) findViewById(R.id.stateMessage)).getText().toString();
        //String image

        if (nickname.length() > 0 && age.length() > 0 && address.length() > 0 && mbti.length() > 0 && stateMessage.length() > 0) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            MemberInfo memberInfo = new MemberInfo(nickname, age, address, mbti, stateMessage);

            if(user!=null) {
                db.collection("users").document(user.getUid()).set(memberInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startToast("회원정보 등록을 성공하였습니다.");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                startToast("회원정보 등록에 실패하였습니다.");
                            }
                        });
            }
        } else {
            startToast("회원정보를 입력해주세요.");
        }
    }

    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}