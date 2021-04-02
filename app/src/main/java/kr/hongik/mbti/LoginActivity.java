package kr.hongik.mbti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


/**
 * class LoginActivity
 *
 * @author 장혜리
 */

public class LoginActivity extends AppCompatActivity {

    //result 상수
    private static final int GG_SIGN_IN = 900;
    private static final int FB_SIGN_IN = 64206;
    //    private static String homeUrl = "http://52.78.50.239:8080"; //webServer
    private static String homeUrl = "http://192.168.35.8:8080"; //Test용 로컬 경로
    final String TAG = LoginActivity.class.getName();
    // 버튼
    private SignInButton btn_login_google;
    private LoginButton btn_login_facebook;
    private FirebaseAuth fbAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLoginButton();

        checkPermission();

        //1.파이어베이스 인증 객체 선언
        fbAuth = FirebaseAuth.getInstance();

        //2.Google Sign In 설정
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btn_login_google.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Create a new instance of GoogleSignInClient
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GG_SIGN_IN);
            }
        });


        //3.facebook login 설정
        mCallbackManager = CallbackManager.Factory.create();
        btn_login_facebook.setReadPermissions("email", "public_profile");
        btn_login_facebook.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        if (fbAuth.getCurrentUser() != null)
            startWebViewActivity(fbAuth.getCurrentUser().getUid());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 구글 로그인
        if (requestCode == GG_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // 로그인 성공
                GoogleSignInAccount account = task.getResult(ApiException.class);
                handleGoogleAccessToken(account);

            } catch (ApiException e) {
                e.printStackTrace();
            }

        } else if (requestCode == FB_SIGN_IN) {
            //페이스북 로그인
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

    }

    private void handleGoogleAccessToken(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Log.d(TAG, "signInWithGoogle:success");
                            // 토큰 얻은 후 google 로그아웃
                            mGoogleSignInClient.signOut();
                            //화면 전환
                            startWebViewActivity(fbAuth.getCurrentUser().getUid());
                        } else {
                            // 로그인 실패
                            Log.w(TAG, "signInWithGoogle:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Log.d(TAG, "signInWithFacebook:success");
                            // 토큰 얻은 후 Facebook 로그아웃
                            LoginManager.getInstance().logOut();
                            // 화면 전환
                            startWebViewActivity(fbAuth.getCurrentUser().getUid());
                        } else {
                            // 로그인 실패
                            Log.w(TAG, "signInWithFacebook:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * 외부저장소 권한허가, 거부 시 프로필 이미지 이용에 문제 있을 수 있음
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    //Toast.makeText(this, "외부 저장소 사용을 위한 읽기/쓰기 권한을 요청합니다", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);

            }
        }
    }

    /**
     * 로그인 버튼 text 변경
     */
    private void setLoginButton() {

        btn_login_google = findViewById(R.id.btn_google_login);
        TextView textView = (TextView) btn_login_google.getChildAt(0);
        textView.setText(getString(R.string.sing_in_google));

        btn_login_facebook = (LoginButton) findViewById(R.id.btn_facebook_login);
        btn_login_facebook.setLoginText(getString(R.string.sing_in_facebook));

    }

    public void startWebViewActivity(String uid) {
        if (uid != null) {
            Toast.makeText(LoginActivity.this, uid + "님이 현재 접속중입니다", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), webViewActivity.class);
            i.putExtra("myurl", homeUrl + "/user/loginPost");
            i.putExtra("uid", uid);
            startActivity(i);
            finish();
        }
    }
}
