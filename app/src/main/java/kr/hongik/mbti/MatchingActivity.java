package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MatchingActivity extends AppCompatActivity {

    private Button btn_matchingOption;
    private static final String TAG = "MatchingActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matching);

        btn_matchingOption = findViewById(R.id.btn_matchingOption);

        btn_matchingOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                matchingAlgorithm();
            }
        });
    }

    private void matchingAlgorithm (){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");

        String m_mbti = ((EditText)findViewById(R.id.m_mbti)).getText().toString();
        String m_minage = ((EditText)findViewById(R.id.m_minage)).getText().toString();
        String m_maxage = ((EditText)findViewById(R.id.m_maxage)).getText().toString();

        if(m_mbti.length()>0 && m_minage.length()>0 && m_maxage.length()>0){
            usersRef.whereEqualTo("mbti", m_mbti)
                    .whereGreaterThanOrEqualTo("age", m_minage)
                    .whereLessThanOrEqualTo("age", m_maxage)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    startToast(document.getId());
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });
        }
        else {
            startToast("옵션을 전부 입력해주세요");
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