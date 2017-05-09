package com.integratingdemo.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.integratingdemo.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TwitterWebViewActivity extends AppCompatActivity {

    @BindView(R.id.webView)
    WebView webView;
    private String tag = TwitterWebViewActivity.class.getSimpleName();

    Context mContext;
    public static String EXTRA_URL = "extra_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_twitter_webview);
        ButterKnife.bind(this);

        try {
            ButterKnife.bind(this);
            mContext = TwitterWebViewActivity.this;
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        try {
            setTitle("Login");
            final String url = this.getIntent().getStringExtra(EXTRA_URL);
            if (null == url) {
                finish();
            }

            webView.setWebViewClient(new MyWebViewClient());
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            try {
                if (url.contains(getResources().getString(R.string.twitter_callback))) {
                    Uri uri = Uri.parse(url);

                    //Sending results back
                    String verifier = uri.getQueryParameter(getString(R.string.twitter_oauth_verifier));
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(getString(R.string.twitter_oauth_verifier), verifier);
                    setResult(RESULT_OK, resultIntent);

                    // closing webview
                    finish();
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Utility.startProgressDialalog(mContext, getResources().getString(R.string.loading));
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            Utility.cancleProgressDialalog(mContext);
            super.onPageFinished(view, url);
        }

    }

}
