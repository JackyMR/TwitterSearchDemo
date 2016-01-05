package com.yuan.twittersearchdemo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yuan.twittersearchdemo.utils.Log;

/**
 * Created by Yuan on 16/1/5.
 */
public class WebActivity extends Activity implements View.OnClickListener {

    private WebView mWebView;

    private ProgressBar progressBar;

    private FloatingActionButton btn_action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mWebView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgressDrawable(new ColorDrawable(Color.MAGENTA));
        progressBar.setBackgroundColor(Color.WHITE);

        initWebView(mWebView);

        String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);

        btn_action = (FloatingActionButton) findViewById(R.id.btn_action);
        btn_action.setOnClickListener(this);
        mWebView.setOnTouchListener((v, event) -> {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    btn_action.hide();
                    break;
                case MotionEvent.ACTION_UP:
                    btn_action.show();
                    break;
                default:
                    btn_action.show();
                    break;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initWebView(WebView webView) {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.i("onProgressChanged = " + newProgress);
                progressBar.setProgress(newProgress);
            }


        });
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_action:
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                }else{
                    onBackPressed();
                }
                break;
            default:
                break;
        }
    }
}
