package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {

    private Button btn_updateButton, mbti_link;

    final String TAG = UpdateActivity.class.getName();
    private ImageView iv_profile;
    ProfileImage profileImage;
    Uri profileImageUri;
    private final int GET_GALLERY_IMAGE = 200;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        mbti_link = findViewById(R.id.mbtiLink2);

        mbti_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.16personalities.com/ko/%EB%AC%B4%EB%A3%8C-%EC%84%B1%EA%B2%A9-%EC%9C%A0%ED%98%95-%EA%B2%80%EC%82%AC");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        btn_updateButton = findViewById(R.id.btn_updateButton);

        iv_profile = (ImageView)findViewById(R.id.ImageForUpdate);
        profileImage = new ProfileImage();
        profileImage.showProfileImage(iv_profile);

        btn_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate2();
                myStartActivity(MyprofileActivity.class);
                finish();
            }
        });

        //iv_profile 클릭시 프로필이미지 재업로드 가능
        iv_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

    }

    private void profileUpdate2(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid())
                .update("nickname",((EditText)findViewById(R.id.u_nickname)).getText().toString(),
                        "age",((EditText)findViewById(R.id.u_age)).getText().toString(),
                        "mbti",((EditText)findViewById(R.id.u_mbti)).getText().toString(),
                        "address",((EditText)findViewById(R.id.u_address)).getText().toString(),
                        "stateMessage",((EditText)findViewById(R.id.u_stateMessage)).getText().toString());

        if(profileImageUri!=null)
            profileImage.uploadProfileImage(profileImageUri);
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_GALLERY_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {
            //imageview에 보여지는 image 변경
            profileImageUri = data.getData();
            iv_profile.setImageURI(profileImageUri);
        }
    }



}