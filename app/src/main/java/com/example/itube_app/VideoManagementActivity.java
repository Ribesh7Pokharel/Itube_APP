package com.example.itube_app;


import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;

public class VideoManagementActivity extends Activity {
    private EditText youtubeUrlEditText;
    private Button playVideoButton;
    private Button addToPlaylistButton;
    private DatabaseHelper dbHelper;
    private int userId = 1; // This should be set from the logged-in user's session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_management);

        dbHelper = new DatabaseHelper(this);
        youtubeUrlEditText = findViewById(R.id.youtube_url);
        playVideoButton = findViewById(R.id.play_video);
        addToPlaylistButton = findViewById(R.id.add_to_playlist);

        playVideoButton.setOnClickListener(v -> {
            String url = youtubeUrlEditText.getText().toString().trim();
            playVideo(url);
        });

        addToPlaylistButton.setOnClickListener(v -> {
            String url = youtubeUrlEditText.getText().toString().trim();
            if (dbHelper.addVideoToPlaylist(userId, url, "Default")) { // Assume "Default" is your default playlist name
                Toast.makeText(this, "Added to Playlist", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to add to Playlist", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playVideo(String videoId) {
        Intent intent = new Intent(this, VideoPlaybackActivity.class);
        intent.putExtra("VIDEO_ID", videoId); // Passing video ID to the playback activity
        startActivity(intent);
    }
}

