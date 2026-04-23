package com.example.istreamapp.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YouTubeUtils {

    public static String extractVideoId(String url) {
        if (url == null || url.trim().isEmpty()) {
            return null;
        }

        String regex = "^(?:https?:\\/\\/)?(?:www\\.)?(?:youtube\\.com\\/watch\\?v=|youtu\\.be\\/)([a-zA-Z0-9_-]{11}).*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        if (matcher.matches()) {
            return matcher.group(1);
        }

        return null;
    }

    public static boolean isValidYouTubeUrl(String url) {
        return extractVideoId(url) != null;
    }
}