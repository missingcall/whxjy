package com.example.myapplication.lcyedu.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.tencent.smtt.export.external.interfaces.WebResourceError;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

public class BrowserActivity extends BaseActivity implements View.OnClickListener {


    private WebView mWebView;
    private String TAG = "BrowserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edu_web);


    }

    @Override
    protected void onResume() {
        super.onResume();

        initView();

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        initWebView();

    }

    // Assuming this code is part of an Android application using WebView
    private void initWebView() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        mWebView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        com.tencent.smtt.sdk.WebSettings webSettings = mWebView.getSettings();
        // 设置支持JS
        if (webSettings != null) {
            webSettings.setJavaScriptEnabled(true);
            // 设置可以访问文件
            webSettings.setAllowFileAccess(true);
            // 设置支持缩放
            webSettings.setBuiltInZoomControls(true);
            webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            // 使用localStorage则必须打开
            webSettings.setDomStorageEnabled(true);
            webSettings.setGeolocationEnabled(true);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e(TAG ,"onPageFinished: ");
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e(TAG ,"onReceivedError: request= " + error.toString() + " onReceivedError: error= " + error.toString());
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView webView, String title) {
                super.onReceivedTitle(webView, title);
                Log.e(TAG ,"onReceivedTitle" + title);
            }
        });

        mWebView.loadUrl("https://gankaoai.gankao.com/grade/1");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }


    @Override
    protected void initListeners() {

    }

}