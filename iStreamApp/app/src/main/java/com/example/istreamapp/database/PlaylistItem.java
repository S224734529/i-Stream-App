package com.example.istreamapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "playlist_items")
public class PlaylistItem {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int userId;
    private String videoUrl;

    public PlaylistItem(int userId, String videoUrl) {
        this.userId = userId;
        this.videoUrl = videoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}