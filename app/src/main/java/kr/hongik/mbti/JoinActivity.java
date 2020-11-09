package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

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

        imageview = (ImageView)findViewById(R.id.image);
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
            public void onClick(View v){
                Intent intent = new Intent(JoinActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}

public void profileUpdate() {
    String nickname =
}