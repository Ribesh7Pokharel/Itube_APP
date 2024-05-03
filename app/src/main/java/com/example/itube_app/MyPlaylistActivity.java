package com.example.itube_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MyPlaylistActivity extends Activity {
    private ListView playlistListView;
    private Button backButton;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<String> playlistItems;
    private int userId = 1; // This should be set based on the logged-in user's context

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        dbHelper = new DatabaseHelper(this);
        playlistListView = findViewById(R.id.playlist_list_view);
        backButton = findViewById(R.id.backButton);

        // Load playlist items from the database
        playlistItems = dbHelper.getUserPlaylists(userId);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistItems);
        playlistListView.setAdapter(adapter);

        // Setup listener for the back button
        backButton.setOnClickListener(v -> {
            // Navigate back to VideoManagementActivity
            Intent intent = new Intent(MyPlaylistActivity.this, VideoManagementActivity.class);
            startActivity(intent);
            finish();  // Close this activity
        });

        // Optional: Setup item click listener for the ListView if you need to handle item clicks
        playlistListView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedVideoUrl = adapter.getItem(position);
            // You can extend this to play the video or open video details
            Toast.makeText(MyPlaylistActivity.this, "Selected: " + selectedVideoUrl, Toast.LENGTH_LONG).show();
        });
    }
}
