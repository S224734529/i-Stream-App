package com.example.istreamapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.istreamapp.R;
import com.example.istreamapp.database.AppDatabase;
import com.example.istreamapp.database.PlaylistItem;
import com.example.istreamapp.utils.SessionManager;
import com.example.istreamapp.utils.YouTubeUtils;

public class HomeActivity extends AppCompatActivity {

    private EditText etVideoUrl;
    private WebView webViewPlayer;
    private AppDatabase db;
    private int userId;
    private String currentUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!SessionManager.isLoggedIn(this)) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_home);

        db = AppDatabase.getInstance(this);
        userId = SessionManager.getLoggedInUserId(this);

        etVideoUrl = findViewById(R.id.etVideoUrl);
        webViewPlayer = findViewById(R.id.webViewPlayer);
        Button btnPlay = findViewById(R.id.btnPlay);
        Button btnAddToPlaylist = findViewById(R.id.btnAddToPlaylist);
        Button btnMyPlaylist = findViewById(R.id.btnMyPlaylist);
        Button btnLogout = findViewById(R.id.btnLogout);

        setupWebView();

        String passedUrl = getIntent().getStringExtra("video_url");
        if (passedUrl != null && !passedUrl.isEmpty()) {
            etVideoUrl.setText(passedUrl);
            playVideo(passedUrl);
        }

        btnPlay.setOnClickListener(v -> {
            String url = etVideoUrl.getText().toString().trim();
            playVideo(url);
        });

        btnAddToPlaylist.setOnClickListener(v -> {
            String url = etVideoUrl.getText().toString().trim();

            if (!YouTubeUtils.isValidYouTubeUrl(url)) {
                Toast.makeText(this, "Enter a valid YouTube URL", Toast.LENGTH_SHORT).show();
                return;
            }

            db.playlistDao().insertPlaylistItem(new PlaylistItem(userId, url));
            Toast.makeText(this, "Added to playlist", Toast.LENGTH_SHORT).show();
        });

        btnMyPlaylist.setOnClickListener(v ->
                startActivity(new Intent(this, PlaylistActivity.class)));

        btnLogout.setOnClickListener(v -> {
            SessionManager.logout(this);
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void setupWebView() {
        WebSettings settings = webViewPlayer.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);

        webViewPlayer.setWebChromeClient(new android.webkit.WebChromeClient());
        webViewPlayer.setWebViewClient(new android.webkit.WebViewClient());
    }

    private void playVideo(String url) {
        String videoId = YouTubeUtils.extractVideoId(url);

        if (videoId == null) {
            Toast.makeText(this, "Invalid YouTube URL", Toast.LENGTH_SHORT).show();
            return;
        }

        String html = "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
                "<style>" +
                "body { margin:0; padding:0; background:#000; }" +
                ".video-container { position:relative; width:100%; height:100vh; }" +
                "iframe { position:absolute; top:0; left:0; width:100%; height:100%; }" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"video-container\">" +
                "<iframe " +
                "src=\"https://www.youtube.com/embed/" + videoId + "?autoplay=1&playsinline=1\" " +
                "frameborder=\"0\" " +
                "allow=\"autoplay; encrypted-media\" " +
                "allowfullscreen>" +
                "</iframe>" +
                "</div>" +
                "</body>" +
                "</html>";

        webViewPlayer.loadDataWithBaseURL(
                "https://www.youtube.com",
                html,
                "text/html",
                "UTF-8",
                null
        );
    }
}