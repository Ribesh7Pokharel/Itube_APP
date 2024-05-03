package com.example.itube_app;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import java.util.List;

public class MyPlaylistActivity extends Activity {
    private ListView playlistListView;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<String> playlistItems;
    private int userId = 1; // This should be dynamically set based on the logged-in user

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_playlist);

        dbHelper = new DatabaseHelper(this);
        playlistListView = findViewById(R.id.playlist_list_view);

        playlistItems = dbHelper.getUserPlaylists(userId);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlistItems);
        playlistListView.setAdapter(adapter);

        playlistListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String videoUrl = adapter.getItem(position);
                boolean deleted = dbHelper.deleteVideoFromPlaylist(userId, videoUrl);
                if (deleted) {
                    playlistItems.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(MyPlaylistActivity.this, "Video removed from playlist", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MyPlaylistActivity.this, "Error removing video", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

