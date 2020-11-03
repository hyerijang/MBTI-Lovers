package kr.hongik.mbti;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 900;
    // 구글로그인 result 상수
    final String TAG = LoginActivity.class.getName();
    // 파이어베이스 인증 객체 생성
    private FirebaseAuth mAuth;
    // 구글api클라이언트
    private GoogleSignInClient mGoogleSignInClient;
    // 구글  로그인 버튼
    private SignInButton btn_login_google;
    private Button signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // 파이어베이스 인증 객체 선언
        mAuth = FirebaseAuth.getInstance();

        btn_login_google = findViewById(R.id.btn_google_logIn);
        signout = findViewById(R.id.signout);

        // Configure Google Sign In
        // gso 개체를 구성할 때 requestIdToken을 호출
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        //Create a new instance of GoogleSignInClient
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        //updateUI();

        btn_login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                logout();
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

//      앱 시작시 로그인 유무 확인
//      updateUI(currentUser);
    }
    // [END on_start_check_user]

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 구글로그인 버튼 응답
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);

            } catch (ApiException e) {

            }
        }
    }

    // 정상적으로 로그인되면 GoogleSignInAccount 개체에서 ID 토큰을 가져와서
    // Firebase 사용자 인증 정보로 교환하고 Firebase 사용자 인증 정보를 사용해 Firebase에 인증한다.
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, mAuth.getUid() + "님이 접속하셨습니다", Toast.LENGTH_SHORT).show();
//                            updateUI();
                        } else {
                            // 로그인 실패
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    public void logout() {
        if (mAuth.getCurrentUser() == null)
            Toast.makeText(LoginActivity.this, "로그인한 유저가 없어 로그아웃 할 수 없습니다", Toast.LENGTH_SHORT).show();
        else {
            mAuth.signOut();
            mGoogleSignInClient.signOut();
            Toast.makeText(LoginActivity.this,   "로그아웃", Toast.LENGTH_SHORT).show();
        }
    }


//    화면전환
//    Note: Activity 간 user data 주고받을 수 있도록 수정할 것
//    public void updateUI(){
//        //로그인된 유저 있는지 확인 후 메인페이지로 이동
//        if (mAuth.getCurrentUser() != null) {
//            Intent intent = new Intent(this, TestActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        else
//        {
//            Toast.makeText(LoginActivity.this, "로그인한 유저 없습니다.", Toast.LENGTH_SHORT).show();
//        }
//
//    }


}
