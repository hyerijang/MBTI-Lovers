package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MatchingPersonActivity extends AppCompatActivity {

    private TextView textView_memberinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching_person);

        Intent intent = getIntent();
        MemberInfo memberInfo = (MemberInfo)intent.getSerializableExtra("MemberInfo");
            textView_memberinfo = (TextView)findViewById(R.id.mp_matchingPerson);
            textView_memberinfo.setText("닉네임 : " + memberInfo.getNickname() + "\n\n나이 : " + memberInfo.getAge() + "\n\nmbti : " + memberInfo.getMbti() + "\n\n주소 : " + memberInfo.getAddress() + "\n\n상태메시지 : " + memberInfo.getStateMessage());
    }
}