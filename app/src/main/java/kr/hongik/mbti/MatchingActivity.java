package kr.hongik.mbti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
                myStartActivity(MainActivity.class);
                finish();
            }
        });
    }

    private void matchingAlgorithm (){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query Q_age = db.collection("users")
                .whereGreaterThanOrEqualTo("age", ((EditText)findViewById(R.id.m_minage)).getText().toString())
                .whereLessThanOrEqualTo("age", ((EditText)findViewById(R.id.m_maxage)).getText().toString());
        Query Q_mbti = db.collection("users")
                .whereEqualTo("mbti", ((EditText)findViewById(R.id.m_mbti)).getText().toString());
    }

    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        startActivityForResult(intent, 1);
    }
}