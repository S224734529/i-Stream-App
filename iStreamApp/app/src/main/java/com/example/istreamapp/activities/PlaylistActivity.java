package com.example.istreamapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istreamapp.R;
import com.example.istreamapp.adapters.PlaylistAdapter;
import com.example.istreamapp.database.AppDatabase;
import com.example.istreamapp.database.PlaylistItem;
import com.example.istreamapp.utils.SessionManager;

import java.util.List;

public class PlaylistActivity extends AppCompatActivity {

    private AppDatabase db;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_playlist);

        db = AppDatabase.getInstance(this);
        userId = SessionManager.getLoggedInUserId(this);

        RecyclerView rvPlaylist = findViewById(R.id.rvPlaylist);
        Button btnLogout = findViewById(R.id.btnLogoutPlaylist);

        List<PlaylistItem> playlist = db.playlistDao().getPlaylistForUser(userId);

        PlaylistAdapter adapter = new PlaylistAdapter(playlist, url -> {
            Intent intent = new Intent(PlaylistActivity.this, HomeActivity.class);
            intent.putExtra("video_url", url);
            startActivity(intent);
        });

        rvPlaylist.setLayoutManager(new LinearLayoutManager(this));
        rvPlaylist.setAdapter(adapter);

        btnLogout.setOnClickListener(v -> {
            SessionManager.logout(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}