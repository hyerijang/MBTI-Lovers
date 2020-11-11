package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateActivity extends AppCompatActivity {

    private Button btn_updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        btn_updateButton = findViewById(R.id.btn_updateButton);

        btn_updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                profileUpdate2();
                myStartActivity(MainActivity.class);
            }
        });

    }

    private void profileUpdate2(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String u_nickname = ((EditText)findViewById(R.id.u_nickname)).getText().toString();
        String u_age = ((EditText)findViewById(R.id.u_age)).getText().toString();
        String u_mbti = ((EditText)findViewById(R.id.u_mbti)).getText().toString();
        String u_address = ((EditText)findViewById(R.id.u_address)).getText().toString();
        String u_stateMessage = ((EditText)findViewById(R.id.u_stateMessage)).getText().toString();

        if(u_address.length()>0 && u_age.length()>0 && u_mbti.length()>0 && u_nickname.length()>0 && u_stateMessage.length()>0){
            db.collection("users").document(user.getUid())
                    .update("nickname",u_nickname,
                            "age",u_age,
                            "mbti", u_mbti,
                            "address", u_address,
                            "stateMessage", u_stateMessage);
        }
        else{
            startToast("정보를 모두 입력해주세요.");
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