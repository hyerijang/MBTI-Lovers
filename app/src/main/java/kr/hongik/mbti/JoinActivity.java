package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;

public class JoinActivity extends AppCompatActivity {

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

        Button button = findViewById(R.id.JoinButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}