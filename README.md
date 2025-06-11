# 🎶 Jamify – Android Music Player App

Jamify is a minimalist and elegant Android music player built using **Java**.  
It scans your device's storage for audio files and plays them in a simple, clean interface.  
Perfect for users who want a straightforward offline music experience!  
*(Bonus: Each song album is cutely represented using a cat image 🐱)*

## 🚀 Features

- 🎧 Play music stored on your device  
- 📃 Dynamic song list with RecyclerView  
- 🎬 Smooth splash screen on launch  
- 🔍 SearchView to find your favorite tracks quickly  
- 🐱 Adorable cat image used for all albums  
- 🔁 Next, previous, pause, and play controls  
- 💜 Stylish UI with bold fonts and vibrant colors  
- 🚫 No internet needed – completely offline!

## 📁 Project Structure

📂 MusicPlayer/  
├── 📁 java/com/sol/musicplayer/  
│   ├── AudioModel.java            # Holds song data model  
│   ├── MainActivity.java          # Home screen showing list of songs  
│   ├── MusicPlayerActivity.java   # Activity to play selected music  
│   ├── MusicListAdapter.java      # Adapter for the RecyclerView  
│   ├── MyMediaPlayer.java         # Media player logic  
│   └── SplashActivity.java        # Splash screen logic  
├── 📁 res/layout/  
│   ├── activity_main.xml          # Layout with search + song list  
│   ├── activity_music_player.xml  # Layout with player UI  
│   ├── activity_splash.xml        # Initial splash screen layout  
│   └── recycler_item.xml          # Individual song item layout  



## 🎯 How It Works

- App launches with a **Splash Screen**  
- The **MainActivity** fetches all local songs and displays them  
- Clicking a song launches **MusicPlayerActivity** to play the track  
- **Media controls** (play/pause/next/prev) are available  
- Songs are displayed with an album placeholder image of a cute **cat 🐱**
