package kr.hongik.mbti;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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
import com.google.firebase.auth.GoogleAuthProvider;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;


/**
 * class LoginActivity
 *
 * @author 장혜리
 */

public class LoginActivity extends AppCompatActivity {

    //result 상수
    private static final int GG_SIGN_IN = 900;
    private static final int FB_SIGN_IN = 64206;
    private static String DomainUrl = "https://mbti-lovers.kro.kr:8080";
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

        //권한 허가
        checkPer();

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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withActivity(this)
                    .withPermissions(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)   // 위치
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) { // 권한 여부를 다 묻고 실행되는 메소드
                            // check if all permissions are granted
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
//                                Toast.makeText(LoginActivity.this, "모든 권한 허용", Toast.LENGTH_SHORT).show();
                                if (uid != null) {
//                                    Toast.makeText(LoginActivity.this, uid + "님이 현재 접속중입니다", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), webViewActivity.class);
                                    i.putExtra("myurl", DomainUrl + "/user/loginPost");
                                    i.putExtra("uid", uid);
                                    startActivity(i);
                                    finish();
                                }
                            }

                        }// onPermissionsChecked()..

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) { // 이전 권한 여부를 거부한 권한이 있으면 실행되는 메소드
                            Toast.makeText(LoginActivity.this, "허가 되지 않은 권한이 있습니다. 권한을 확인해주세요.", Toast.LENGTH_LONG).show();        // 거부한 권한 이름이 저장된 list
                            showSettingsDialog(); // 권한 거부시 앱 정보 설정 페이지를 띄우기 위한 임의 메소드
                            if (uid != null) {
//                                Toast.makeText(LoginActivity.this, uid + "님이 현재 접속중입니다", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(getApplicationContext(), webViewActivity.class);
                                i.putExtra("myurl", DomainUrl + "/user/loginPost");
                                i.putExtra("uid", uid);
                                startActivity(i);
                                finish();
                            }
                        }// onPermissionRationaleShouldBeShown()..
                    })
                    .check();
        }


    }


    // 만약 권한을 거절했을 경우,  다이얼로그 띄우기 위한 임의 메소드
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Need Permissions");
        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.");
        builder.setPositiveButton("GOTO SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                openSettings(); // 어플리케이션 정보 설정 페이지 띄움.
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }// showSettingsDialog()..

    // 어플리케이션 정보 설정 페이지
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }// openSettings()..


    void checkPer() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Dexter.withActivity(this)
                    .withPermissions(
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_FINE_LOCATION)   // 위치
                    .withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) { // 권한 여부를 다 묻고 실행되는 메소드
                            // check if all permissions are granted
                            if (multiplePermissionsReport.areAllPermissionsGranted()) {
                                Toast.makeText(LoginActivity.this, "모든 권한 허용", Toast.LENGTH_SHORT).show();
                            }

                        }// onPermissionsChecked()..

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) { // 이전 권한 여부를 거부한 권한이 있으면 실행되는 메소드
                            Toast.makeText(LoginActivity.this, "허가 되지 않은 권한이 있습니다. 권한을 확인해주세요.", Toast.LENGTH_LONG).show();
                            showSettingsDialog(); // 권한 거부시 앱 정보 설정 페이지를 띄우기 위한 임의 메소드
                        }// onPermissionRationaleShouldBeShown()..
                    })
                    .check();
        }

    }

}
