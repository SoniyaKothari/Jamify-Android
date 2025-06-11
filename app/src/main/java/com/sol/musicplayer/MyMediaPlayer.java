package com.sol.musicplayer;

import android.media.MediaPlayer;

//singleton class where only 1 MediaPlayer instance is created
public class MyMediaPlayer
{
    static MediaPlayer instance;
    //only 1 audio will play at a time

    public static MediaPlayer getInstance()
    {
        if(instance == null)
        {
            instance = new MediaPlayer();
        }
        return instance;
    }

    public static int currentIndex = -1;
}
