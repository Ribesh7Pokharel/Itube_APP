package com.example.itube_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class VideoManagementActivity extends Activity {
    private EditText youtubeUrlEditText;
    private Button playVideoButton;
    private Button addToPlaylistButton;
    private Button myPlaylistButton;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_management);

        dbHelper = new DatabaseHelper(this);  // Assuming DatabaseHelper is properly implemented

        youtubeUrlEditText = findViewById(R.id.youtube_url);
        playVideoButton = findViewById(R.id.play_video);
        addToPlaylistButton = findViewById(R.id.add_to_playlist);
        myPlaylistButton = findViewById(R.id.my_playlist);

        playVideoButton.setOnClickListener(v -> {
            String youtubeUrl = youtubeUrlEditText.getText().toString().trim();
            String videoId = extractVideoId(youtubeUrl);
            if (videoId == null || videoId.trim().isEmpty()) {
                Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
            } else {
                loadYouTubeVideo(videoId);
            }
        });

        addToPlaylistButton.setOnClickListener(v -> {
            String videoUrl = youtubeUrlEditText.getText().toString().trim();
            addToPlaylist(videoUrl);
        });

        myPlaylistButton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoManagementActivity.this, MyPlaylistActivity.class);
            startActivity(intent);
        });
    }

    private void playVideo(String videoId) {
        if (!videoId.isEmpty()) {
            Intent intent = new Intent(VideoManagementActivity.this, VideoPlaybackActivity.class);
            intent.putExtra("VIDEO_ID", videoId);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToPlaylist(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().isEmpty()) {
            Toast.makeText(this, "Video URL cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assuming a method to extract the video ID if your database expects only the ID
        String videoId = extractVideoId(videoUrl);
        if (videoId == null || videoId.isEmpty()) {
            Toast.makeText(this, "Invalid video URL", Toast.LENGTH_SHORT).show();
            return;
        }

        // Assume user ID and playlist name are predefined or obtained from user session/context
        int userId = 1; // This should ideally come from user session or context
        String playlistName = "Default"; // This could also be dynamically set based on user input or selection

        try {
            if (dbHelper.addVideoToPlaylist(userId, videoId, playlistName)) {
                Toast.makeText(this, "Added to Playlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add to Playlist", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Failed to add to Playlist: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("VideoManagementActivity", "Error adding video to playlist", e);
        }
    }



    // Extracts video ID from URL, assuming the URL is a standard YouTube link
    private String extractVideoId(String url) {
        Uri parsedUri = Uri.parse(url);
        String videoId = parsedUri.getQueryParameter("v");
        if (videoId == null) {
            // Check if the URL is a short YouTube URL which is handled differently
            if (url.contains("youtu.be/")) {
                videoId = url.substring(url.lastIndexOf("/") + 1);
            }
        }
        return videoId;
    }

    private void loadYouTubeVideo(String videoId) {
        // Assuming you have a WebView or YouTubePlayerView setup to play the video
        Intent intent = new Intent(this, VideoPlaybackActivity.class);
        intent.putExtra("VIDEO_ID", videoId);
        startActivity(intent);
    }
}


