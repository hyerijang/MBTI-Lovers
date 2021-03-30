package kr.hongik.mbti;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


/**
 * class LoginActivity
 *
 * @author 장혜리
 */

public class LoginActivity extends AppCompatActivity {

    //result 상수
    private static final int RC_SIGN_IN = 900;
    final String TAG = LoginActivity.class.getName();
    // 버튼
    private SignInButton btn_login_google;
    private LoginButton btn_login_facebook;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setLoginButton();

        checkPermission();

        //1.파이어베이스 인증 객체 선언
        mFirebaseAuth = FirebaseAuth.getInstance();

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
                startActivityForResult(signInIntent, RC_SIGN_IN);
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
        FirebaseUser currentUser = mFirebaseAuth.getCurrentUser();
        if(currentUser!= null)
            startWebViewActivity(currentUser.getUid());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //페이스북 로그인
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

        // 구글 로그인
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


    /**
     * google Token으로 firebase 로그인
     * 공식 문서를 바탕으로 변형하였습니다. 자세한 사항은 공식 문서를 참조바랍니다.
     *
     * @param acct
     */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Log.d(TAG, "signInWithGoogle:success");
                            // 토큰 얻은 후 google 로그아웃
                            mGoogleSignInClient.signOut();
                            checkUser(mFirebaseAuth.getCurrentUser());
                        } else {
                            // 로그인 실패
                            Log.w(TAG, "signInWithGoogle:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }


    /**
     * facebook Token으로 firebase 로그인
     * 공식 문서를 바탕으로 변형하였습니다. 자세한 사항은 공식 문서를 참조바랍니다.
     *
     * @param token
     */
    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Log.d(TAG, "signInWithFacebook:success");
                            // 토큰 얻은 후 Facebook 로그아웃
                            LoginManager.getInstance().logOut();
                            checkUser(mFirebaseAuth.getCurrentUser());
                        } else {
                            // 로그인 실패
                            Log.w(TAG, "signInWithFacebook:failure", task.getException());
                            Toast.makeText(LoginActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    /**
     * firebae 유저 확인 및 화면 전환
     *
     * @param user
     */
    public void checkUser(FirebaseUser user) {
        if (user != null) {
//            Toast.makeText(LoginActivity.this, mFirebaseAuth.getUid() + "님이 현재 접속중입니다", Toast.LENGTH_SHORT).show();
            loginCheck(mFirebaseAuth.getUid());
//            myStartActivity(MainActivity.class);
//            finish();
        }

    }


    private void myStartActivity(Class c) {
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
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

//    private static String homeUrl = "http://52.78.50.239:8080"; //webServer
    private static String homeUrl = "http://192.168.35.8:8080"; //Test용 로컬 경로

    private void loginCheck(final String uid) {
        class LoginAsync extends AsyncTask<String, Void, String> {
            private Dialog loadingDialog;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loadingDialog = ProgressDialog.show(LoginActivity.this, "Please wait", "Loading...");
            }

            @Override
            protected String doInBackground(String... params) {
                InputStream is = null;
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                nameValuePairs.add(new BasicNameValuePair("uid", params[0]));

                String result = null;

                //HttpClient에 Post 방식으로 Uid 전송
                try {
                    Log.d(TAG, "해당 uid가 DB에 있는지 검증");
                    HttpClient httpClient = SessionControl.getHttpclient();

                    String str = homeUrl + "/login/auth/"+uid;
                    HttpPost httpPost = new HttpPost(str);
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    HttpResponse response = httpClient.execute(httpPost);
                    HttpEntity entity = response.getEntity();

                    is = entity.getContent();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    result = sb.toString();


                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onPostExecute(String result) {
                String s = result.trim();
                Log.d(TAG, "Current User = " + s);
                if (s.contains(uid)) {
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_LONG).show();
                     startWebViewActivity(uid);
                } else {
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();

                }
            }
        }

        new LoginAsync().execute(uid);

    }

    public void startWebViewActivity(String uid) {
        if (uid != null) {
            Toast.makeText(LoginActivity.this, uid + "님이 현재 접속중입니다", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), webViewActivity.class);
            i.putExtra("myurl", homeUrl + "/user/loginPost?uid="+uid);
            startActivity(i);
            finish();
        }
    }
}
