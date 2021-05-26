package kr.hongik.mbti;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.apache.http.util.EncodingUtils;

public class webViewActivity extends Activity {

    private WebView wv;
    private WebSettings settings; //웹뷰세팅
    private ValueCallback mFilePathCallback;
    private static final String TAG = "webView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        String myUrlAddress = intent.getStringExtra("myurl");
        String postData = "uid=" + intent.getStringExtra("uid");

        wv = findViewById(R.id.wv);
        wv.setWebViewClient(new WebViewClient(){

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.equalsIgnoreCase("https://mbti-lovers.kro.kr:8080/matching")) {
                    Log.i(TAG, "매칭 시작");
                    turnGpsLocationOn();  // 위치 체크
                }

                super.onPageStarted(view, url, favicon);
            }
        });
        wv.postUrl(myUrlAddress, EncodingUtils.getBytes(postData, "BASE64"));

        setWebView(wv);
    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

    }

    public void setWebView(WebView wv) {
        //세부 세팅 등록
        settings = wv.getSettings();
        settings.setJavaScriptEnabled(true); // 웹페이지 자바스크립트 허용 여부
        settings.setDomStorageEnabled(true); // 로컬저장소 허용 여부
        settings.setJavaScriptCanOpenWindowsAutomatically(true); //팝업창 허영
        settings.setLoadsImagesAutomatically(true); //웹뷰가 앱의 이미지 리소스 로드
//        settings.setUseWideViewPort(true); //wide viewport 사용
        settings.setSupportZoom(false); //zoom 비허용
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); //캐시 비허용 : 웹뷰 속도 향상
        settings.setAppCacheEnabled(false); //앱 내부 캐시 사용 안함
        settings.setDomStorageEnabled(true); //로컬스토리지 사용 허용
        settings.setAllowFileAccess(true);//웹뷰에서 파일 액세스 활성화
        settings.setGeolocationEnabled(true);//위치 허용
        setWebChromeClient();

    }

    public void setWebChromeClient() {
        wv.setWebChromeClient(new WebChromeClient() {
            //파일업로드
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback filePathCallback, FileChooserParams fileChooserParams) {
                mFilePathCallback = filePathCallback;

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");

                startActivityForResult(intent, 0);
                return true;
            }

            //gps
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                super.onGeolocationPermissionsShowPrompt(origin, callback);
                callback.invoke(origin, true, false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.e("resultCode:: ", String.valueOf(resultCode));
        if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mFilePathCallback.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode, data));
            } else {
                mFilePathCallback.onReceiveValue(new Uri[]{data.getData()});
            }

            mFilePathCallback = null;
        } else if (data != null) {

        } else {
            mFilePathCallback.onReceiveValue(null);
        }
    }


    //    웹뷰에서 뒤로가기 했을 때 히스토리가 남아있을경우 그 전 페이지로
    private long backBtnTime = 0;

    @Override
    public void onBackPressed() {
        long curTime = System.currentTimeMillis();
        long gapTime = curTime - backBtnTime;
        //메인페이지에서 뒤로가기 누르면 바로 종료
        if (wv.getUrl().equalsIgnoreCase("https://mbti-lovers.kro.kr:8080/")) {
            super.onBackPressed();
        } else if (wv.canGoBack()) {
            wv.goBack();
        } else if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed();
        } else {
            backBtnTime = curTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }


    public void turnGpsLocationOn() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient client = LocationServices.getSettingsClient(this);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // All location settings are satisfied. The client can initialize
                // location requests here.
                // ...
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        ResolvableApiException resolvable = (ResolvableApiException) e;
                        resolvable.startResolutionForResult(webViewActivity.this,
                                0x1);
                    } catch (IntentSender.SendIntentException sendEx) {
                        // Ignore the error.
                    }
                }
            }
        });
    }

}


