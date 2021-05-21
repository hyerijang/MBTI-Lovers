package kr.hongik.mbti;

import android.app.Activity;
import android.content.Intent;
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

import androidx.annotation.Nullable;

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
        wv.setWebViewClient(new WebViewClient());
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
        } else {
            mFilePathCallback.onReceiveValue(null);
        }
    }
}


