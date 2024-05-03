package com.example.itube_app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class VideoPlaybackActivity extends Activity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_playback);

        webView = findViewById(R.id.webview);
        webView.setWebViewClient(new WebViewClient()); // Handle navigation
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        String videoId = getIntent().getStringExtra("VIDEO_ID"); // Get the video ID
        loadYouTubeVideo(videoId);
    }

    private void loadYouTubeVideo(String videoId) {
        String frameVideo = "<html><body><iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoId + "\" frameborder=\"0\" allowfullscreen></iframe></body></html>";
        webView.loadData(frameVideo, "text/html", "utf-8");
    }
}

