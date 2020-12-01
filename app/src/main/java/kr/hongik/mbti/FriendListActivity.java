package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendListActivity extends AppCompatActivity {

    final String TAG = FriendListActivity.class.getName();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference docRef;
    CollectionReference colRef;

    public ArrayList<FriendVO> myFreindList = new ArrayList<FriendVO>();

    FriendListAdapter adapter = new FriendListAdapter(this, 0, myFreindList);

    ListView lv_friends;

    final Map<String, Object> emptyObject = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        lv_friends = (ListView) findViewById(R.id.user_friends);

        colRef = db.collection("friendList/"+user.getUid()+"/FriendUserNums");

        //친구추가
        colRef.document("4MEeO638oeZqTzaQ545suXEOgF82").set(emptyObject);
        colRef.document("YP6jS2oqzPc4gSaAGXBGJB5aWIf1").set(emptyObject);
        colRef.document("iB6i0ojaQ8d8SWuGYNzo4gb87J92").set(emptyObject);

        //친구목록 read
        colRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String friendUserNum =document.getId();
                        getFriendinfo(friendUserNum);
                    }
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

        
        lv_friends.setAdapter(adapter);

    }


    /**
     * 친구의 userNum으로 친구정보를 가져오는 함수
     * @param userNum
     */
    public void getFriendinfo(String userNum){

        docRef = db.document("users/"+userNum);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        myFreindList.add(new FriendVO(userNum, document.getString("nickname"), document.getString("stateMessage"), document.getString("mbti")));
                        adapter.notifyDataSetChanged();
                        Log.d(TAG, "current Friendlist size : "+ myFreindList.size());
                    }
                }
            }
        });
    }



}

