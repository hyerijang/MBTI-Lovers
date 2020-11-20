package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CompatibilityActivity extends AppCompatActivity {

    private TextView textView_memberinfo2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compatibility);

        Intent intent = getIntent();
        MemberInfo memberInfo = (MemberInfo)intent.getSerializableExtra("MemberInfo");
        textView_memberinfo2 = (TextView)findViewById(R.id.mp_MatchingPerson2);
        textView_memberinfo2.setText("닉네임 : " + memberInfo.getNickname() + "\n\n성별 : " + memberInfo.getGender() + "\n\n나이 : " + memberInfo.getAge() + "\n\nmbti : " + memberInfo.getMbti() + "\n\n주소 : " + memberInfo.getAddress() + "\n\n상태메시지 : " + memberInfo.getStateMessage());
    }
}