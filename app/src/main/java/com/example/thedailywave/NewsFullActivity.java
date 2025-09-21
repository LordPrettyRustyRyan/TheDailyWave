package com.example.thedailywave;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.OnBackPressedCallback;

public class NewsFullActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);

        webView = findViewById(R.id.web_view);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleLarge);
        progressBar.setIndeterminate(true);

        String url = getIntent().getStringExtra("url");

        if (url != null && !url.isEmpty()) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageFinished(WebView view, String url) {
                    progressBar.setVisibility(View.GONE);
                }
            });

            progressBar.setVisibility(View.VISIBLE);
            webView.loadUrl(url);

            getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        setEnabled(false); // disable callback
                        NewsFullActivity.super.onBackPressed();
                    }
                }
            });
        }
    }

}
