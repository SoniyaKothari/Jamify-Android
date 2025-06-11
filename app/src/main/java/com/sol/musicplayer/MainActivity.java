package com.sol.musicplayer;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    RecyclerView reycle_v;
    TextView no_s;
    ArrayList<AudioModel> listOfSongs = new ArrayList<>();
    SearchView searchView;
    private MusicListAdapter songAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reycle_v=findViewById(R.id.recycle);
        no_s=findViewById(R.id.no_songs);
        searchView = findViewById(R.id.search_view);


        if(checkPermission() == false)
        {
            requestPermission();
            return;
        }

        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DURATION,
        };

        //only requests audio files and not ringtones and alarms
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        //fetch audio files from device's storage
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,projection,selection,null,null);
        while(cursor.moveToNext())
        {
            AudioModel songData = new AudioModel(cursor.getString(1),cursor.getString(0),cursor.getString(2));
            if(new File(songData.getPath()).exists())
                listOfSongs.add(songData);
        }

        //if no songs found
        if(listOfSongs.size()==0)
        {
            no_s.setVisibility(View.VISIBLE);
        }
        else //if found
        {
            //recyclerview
            //responsible for organizing items in recyclerview
            reycle_v.setLayoutManager(new LinearLayoutManager(this));
            songAdapter = new MusicListAdapter(listOfSongs, getApplicationContext());
            reycle_v.setAdapter(songAdapter);
        }
        setupSearch();
    }

    //implementing song search
    private void setupSearch()
    {
        //triggers this listener when user types texts
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                filterSongs(newText);
                return true;
            }
        });
    }

    //filter songs dynamically
    private void filterSongs(String query)
    {
        ArrayList<AudioModel> filteredList = new ArrayList<>();
        //checks
        for(AudioModel song: listOfSongs)
        {
            if(song.getTitle().toLowerCase().contains(query.toLowerCase()))
            {
                //if found then add that song to that list
                filteredList.add(song);
            }
        }

        //if not found
        if(filteredList.isEmpty())
        {
            no_s.setVisibility(View.VISIBLE);
        }
        else // hide that textview of found
        {
            no_s.setVisibility(View.GONE);
        }

        //updates the list
        if (songAdapter != null)
        {
            songAdapter.updateList(filteredList);
        }

    }

    //1
    boolean checkPermission()
    {
        //checks if a permission is granted or not
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_MEDIA_AUDIO)
            == PackageManager.PERMISSION_GRANTED;
    }

    //2 asks permission from user using system dialog(pop-up shown by android)
    void requestPermission()
    {
        //if denied
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_MEDIA_AUDIO))
        {
            Toast.makeText(this, "READ PERMISSION IS REQUIRED, PLEASE ALLOW FROM SETTINGS", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_MEDIA_AUDIO}, 123);
        }
    }

    //when the which songs are being played(this is set)
    @Override
    protected void onResume()
    {
        super.onResume();
        //avoid NULLPOINTER Exception
        if (reycle_v != null && songAdapter != null)
        {
            songAdapter.notifyDataSetChanged(); // Refresh adapter instead of creating a new one
        }

    }
}