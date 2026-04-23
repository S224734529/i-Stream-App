package com.example.istreamapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.istreamapp.R;
import com.example.istreamapp.database.PlaylistItem;

import java.util.List;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder> {

    public interface OnPlaylistClickListener {
        void onPlaylistClick(String url);
    }

    private final List<PlaylistItem> list;
    private final OnPlaylistClickListener listener;

    public PlaylistAdapter(List<PlaylistItem> list, OnPlaylistClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_playlist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        PlaylistItem item = list.get(position);
        holder.tvUrl.setText(item.getVideoUrl());

        holder.itemView.setOnClickListener(v -> listener.onPlaylistClick(item.getVideoUrl()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrl;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUrl = itemView.findViewById(R.id.tvPlaylistUrl);
        }
    }
}