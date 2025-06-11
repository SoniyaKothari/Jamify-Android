package com.sol.musicplayer;

import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class MusicPlayerActivity extends AppCompatActivity
{
    TextView titleTv, currentTimetv, totalTimetv;
    SeekBar seekBar;
    ImageView pausePlay, nextBtn, previousBtn, musicIcon;
    ArrayList<AudioModel> songsList;
    AudioModel currentSong;
    MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);

        titleTv=findViewById(R.id.song_title);
        currentTimetv=findViewById(R.id.current_time);
        totalTimetv=findViewById(R.id.total_time);
        seekBar=findViewById(R.id.seek_bar);
        pausePlay=findViewById(R.id.pause_play);
        nextBtn=findViewById(R.id.next);
        previousBtn=findViewById(R.id.previous);
        musicIcon=findViewById(R.id.music_icon);

        //enables marquee effect
        titleTv.setSelected(true);

        //gets the list of songs that got from another activity using Intent
        songsList = (ArrayList<AudioModel>) getIntent().getSerializableExtra("LIST");

        setResourceWithMusic();

        //setting the seekbar's position
        MusicPlayerActivity.this.runOnUiThread(new Runnable()//update UI element on main thread
        {
            @Override
            public void run()
            {
                if(mediaPlayer != null)
                {
                    //how much of song has played so far
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    //passing it as a String
                    currentTimetv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    //set the pause_play btn
                    if(mediaPlayer.isPlaying())
                    {
                        //sets imageview using resource id
                        pausePlay.setImageResource(R.drawable.baseline_pause_circle_24);
                    }
                    else
                    {
                        pausePlay.setImageResource(R.drawable.play_arrow);
                    }
                }

                //this makes the code inside run() repeat every 100ms
                //at each milliseconds it will check the mediaPlayer and update the seekBar
                new Handler().postDelayed(this,100);
            }
        });
        //if the user moves the seekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                //changed by user
                if(mediaPlayer != null && fromUser)
                {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {

            }
        });
    }

    //currentSong's details
    void setResourceWithMusic()
    {
        currentSong = songsList.get(MyMediaPlayer.currentIndex);
        titleTv.setText(currentSong.getTitle());
        totalTimetv.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener((v-> playNextSong()));
        previousBtn.setOnClickListener(v-> playPreviousSong());

        playMusic();
    }

    //this method plays the selected song and sets up the SeekBar to track its progress
    private void playMusic()
    {
        mediaPlayer.reset();

        try
        {
            mediaPlayer.setDataSource(currentSong.getPath());//sets filepath of current song
            mediaPlayer.prepare(); // loads the file
            mediaPlayer.start(); //plays
            seekBar.setProgress(0); //start with 0
            seekBar.setMax(mediaPlayer.getDuration()); //endpoint of seekbar
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void playNextSong()
    {
        //if it is the last song
        if(MyMediaPlayer.currentIndex ==  songsList.size()-1)
        {
            return;
        }
        MyMediaPlayer.currentIndex +=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void playPreviousSong()
    {
        if(MyMediaPlayer.currentIndex == 0)
        {
            return;
        }
        MyMediaPlayer.currentIndex -=1;
        mediaPlayer.reset();
        setResourceWithMusic();
    }

    private void pausePlay()
    {
        if(mediaPlayer.isPlaying())
        {
            mediaPlayer.pause();
        }
        else
        {
            mediaPlayer.start();
        }
    }

    public static String convertToMMSS(String duration)
    {
        Long mills = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(mills) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(mills) % TimeUnit.MINUTES.toSeconds(1));
    }
}