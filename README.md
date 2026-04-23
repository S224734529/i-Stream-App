# i-Stream-App
```markdown
# iStream App

The iStream App is an Android application developed in Java. It demonstrates Android media playback, local user authentication, Room database integration, playlist management, and session handling.

## Overview

This application allows users to create an account, log in, enter a YouTube video URL, play the video within the app, save the URL to a personal playlist, and revisit saved videos later. Each user's playlist is stored separately in a local Room database.

## Features

### Authentication
- Login screen with username and password
- Sign up screen with:
  - full name
  - username
  - password
  - confirm password
- User credentials stored in Room database
- Validation for login and sign up

### Home Screen
- Enter YouTube video URL
- Play video using WebView and YouTube iFrame embed
- Add current URL to playlist
- Navigate to My Playlist screen
- Logout button

### Playlist Screen
- Displays saved video URLs for the logged-in user
- Each URL is clickable
- Selected item loads the video again on the home screen
- Logout button available

## Application Flow

Login Screen  
→ Home Screen  
→ Play video or add to playlist  
→ Open My Playlist  
→ Select saved URL to reload on Home Screen

Users without an account can go from:

Login Screen  
→ Sign Up Screen  
→ Create account  
→ Return to Login

## Technologies Used

- Java
- Android Studio
- Room Database
- SQLite via Room
- SharedPreferences
- WebView
- YouTube iFrame Embed
- RecyclerView
- ConstraintLayout
- LinearLayout

## Project Structure

```text
com.example.istreamapp
│
├── activities
│   ├── LoginActivity.java
│   ├── SignUpActivity.java
│   ├── HomeActivity.java
│   └── PlaylistActivity.java
│
├── database
│   ├── AppDatabase.java
│   ├── User.java
│   ├── PlaylistItem.java
│   ├── UserDao.java
│   └── PlaylistDao.java
│
├── adapters
│   └── PlaylistAdapter.java
│
├── utils
│   ├── SessionManager.java
│   └── YouTubeUtils.java
