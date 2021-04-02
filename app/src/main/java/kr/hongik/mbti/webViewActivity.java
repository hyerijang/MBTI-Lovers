package kr.hongik.mbti;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import org.apache.http.util.EncodingUtils;

public class webViewActivity extends Activity {

    private WebView wv;
    private WebSettings settings; //웹뷰세팅

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        setWebView();

        String myUrlAddress = intent.getStringExtra("myurl");
        wv = findViewById(R.id.wv);
        String postData = "uid=" + intent.getStringExtra("uid");
        
        wv.postUrl(myUrlAddress, EncodingUtils.getBytes(postData, "BASE64"));
    }

    public void onResume() {
        super.onResume();

    }

    public void onPause() {
        super.onPause();

    }

    public void setWebView() {

        wv.setWebViewClient(new WebViewClient());
        wv.setWebChromeClient(new WebChromeClient()); //콜백 허용

        settings = wv.getSettings(); //세부 세팅 등록
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
    }
}


