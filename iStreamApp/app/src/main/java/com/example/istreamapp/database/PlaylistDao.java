package com.example.istreamapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlaylistDao {

    @Insert
    void insertPlaylistItem(PlaylistItem item);

    @Query("SELECT * FROM playlist_items WHERE userId = :userId")
    List<PlaylistItem> getPlaylistForUser(int userId);
}