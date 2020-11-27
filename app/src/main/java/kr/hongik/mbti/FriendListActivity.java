package kr.hongik.mbti;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FriendListActivity extends AppCompatActivity {

    FirebaseUser user;
    FirebaseFirestore db;
    DocumentReference usersRef;

    ArrayList<String> userNum;
    ArrayList<String> nicknames;
    ArrayList<String> stateMessages;

    ListView lv_friends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        usersRef =  db.collection("users").document(user.getUid());

        lv_friends = (ListView) findViewById(R.id.user_friends);
        
        //데이터 가져옴

//        ArrayList<MemberInfo> f = new ArrayList<>();

        userNum = new ArrayList<>();
        nicknames = new ArrayList<>();
        stateMessages = new ArrayList<>();
        makeSampleData();

        //data setting
        CustomAdapter adapter = new CustomAdapter(this, 0, nicknames, stateMessages);
        lv_friends.setAdapter(adapter);
        
    }


    public void makeSampleData(){
        userNum.add(user.getUid());
        nicknames.add("나");
        stateMessages.add("안녕");
        // 샘플 데이터를 생성합니다.
        int numOfFreinds = 30;
        for(int i = 1; i <= numOfFreinds; i++) {
            nicknames.add("user" + i);
            stateMessages.add("hello user" + i);
        }
    }


    private class CustomAdapter extends ArrayAdapter<String> {
        private ArrayList<String> nicknames;
        private ArrayList<String> stateMessages;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<String> array1,ArrayList<String> array2) {
            super(context, textViewResourceId, array1);
            this.nicknames = array1;
            this.stateMessages = array2;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.user, null);
            }

            //프로필 이미지
            ImageView iv_profileImage = (ImageView)v.findViewById(R.id.profileImage);

            //이름
            TextView tv_nickname = (TextView)v.findViewById(R.id.tv_nickname);
            tv_nickname.setText(nicknames.get(position));

            //상태메세지
            TextView tv_state_message = (TextView) v.findViewById(R.id.tv_state_message);
            tv_state_message.setText(stateMessages.get(position));
            return v;
        }
    }


}

