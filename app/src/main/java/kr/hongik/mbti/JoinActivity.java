package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Button joinbutton;
    private static final String TAG = "JoinActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
        imageview = (ImageView)findViewById(R.id.Image);
        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, GET_GALLERY_IMAGE);
            }
        });

        TextInputLayout inputLayout = findViewById(R.id.nickname);
        TextInputLayout inputLayout1 = findViewById(R.id.age);
        TextInputLayout inputLayout2 = findViewById(R.id.mbti);
        TextInputLayout inputLayout3 = findViewById(R.id.address);
        TextInputLayout inputLayout4 = findViewById(R.id.stateMessage);
        inputLayout.setCounterEnabled(true);
        inputLayout.setCounterMaxLength(20);
        inputLayout1.setCounterEnabled(true);
        inputLayout1.setCounterMaxLength(3);
        inputLayout2.setCounterEnabled(true);
        inputLayout2.setCounterMaxLength(4);
        inputLayout3.setCounterEnabled(true);
        inputLayout3.setCounterMaxLength(50);
        inputLayout4.setCounterEnabled(true);
        inputLayout4.setCounterMaxLength(50);

        joinbutton = findViewById(R.id.JoinButton);

        joinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate();
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode==GET_GALLERY_IMAGE && resultCode==RESULT_OK && data !=null&&data.getData()!=null){
            Uri selectedImageUri = data.getData();
            imageview.setImageURI(selectedImageUri);
        }
    }

    private void profileUpdate(){
        String nickname = ((EditText)findViewById(R.id.nickname)).getText().toString();
        String age = ((EditText)findViewById(R.id.age)).getText().toString();
        String mbti  = ((EditText)findViewById(R.id.mbti)).getText().toString();
        String address = ((EditText)findViewById(R.id.address)).getText().toString();
        String stateMessage = ((EditText)findViewById(R.id.stateMessage)).getText().toString();

        if(nickname.length()>0 && age.length()>0 && mbti.length()>0 && address.length()>0 && stateMessage.length()>0){
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            MemberInfo memberInfo = new MemberInfo(nickname, age, address, mbti, stateMessage);

            if(user != null) {
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
                                Log.w(TAG, "Error writing document", e);
                            }
                        });
            }
        }else {
            startToast("회원정보를 입력해주세요.");
        }

    }

    private void startToast(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}