package com.example.itube_app;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

public class PlaylistViewerActivity extends Activity {
    private ListView playlistsListView;
    private DatabaseHelper dbHelper;
    private ArrayAdapter<String> adapter;
    private List<String> playlists;
    private int userId = 1; // This should be set from the logged-in user's session

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_viewer);

        dbHelper = new DatabaseHelper(this);
        playlistsListView = findViewById(R.id.playlists_list_view);

        playlists = dbHelper.getAllPlaylists(userId);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playlists);
        playlistsListView.setAdapter(adapter);

        playlistsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedPlaylist = adapter.getItem(position);
                Intent intent = new Intent(PlaylistViewerActivity.this, MyPlaylistActivity.class);
                intent.putExtra("playlistName", selectedPlaylist);
                startActivity(intent);
            }
        });
    }
}
