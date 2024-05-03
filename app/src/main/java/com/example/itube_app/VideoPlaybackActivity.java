package com.example.itube_app;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class VideoPlaybackActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        webView = findViewById(R.id.webview);
        setupWebView();
        String videoId = getIntent().getStringExtra("VIDEO_ID");
        if (videoId != null && !videoId.isEmpty()) {
            loadYouTubeVideo(videoId);
        } else {
            Toast.makeText(this, "Invalid video ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupWebView() {
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed(); // Handle SSL error during loading
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // setAppCacheEnabled is no longer available and should be removed
    }

    private void loadYouTubeVideo(String videoId) {
        String frameVideo = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        webView.loadData(frameVideo, "text/html", "utf-8");
    }
}



