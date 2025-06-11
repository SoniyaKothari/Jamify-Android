package com.sol.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder>
{
    ArrayList<AudioModel> songsList;
    Context context;

    public MusicListAdapter(ArrayList<AudioModel> songsList, Context context)
    {
        this.songsList = songsList;
        this.context = context;
    }

    //update List dynamically
    public void updateList(ArrayList<AudioModel> newList)
    {
        songsList=newList;
        notifyDataSetChanged(); //refresh recycler view when search is applied
    }

    //called only when new view is needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        return new MusicListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        AudioModel songData = songsList.get(position);
        holder.tittleTextView.setText(songData.getTitle());

        //highlight the music which is being played (update in recyclerView as well)
        if(MyMediaPlayer.currentIndex==position) //the song being played
        {
            holder.tittleTextView.setTextColor(Color.parseColor("#D76BA2"));
        }
        else
        {
            holder.tittleTextView.setTextColor(Color.parseColor("#000000"));
        }

        //click each songs
        holder.itemView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //navigate to another activity
                MyMediaPlayer.getInstance().reset();
                MyMediaPlayer.currentIndex=position;
                Intent intent = new Intent(context, MusicPlayerActivity.class);
                intent.putExtra("LIST",songsList);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    //tells recyclerview how many items to show
    @Override
    public int getItemCount()
    {
        return songsList.size();
    }

    //holds references of views inside recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView tittleTextView;
        ImageView iconImageView;
        public ViewHolder(View itemView)//itemView is one single item (each songs data)
        {
            super(itemView);
            tittleTextView=itemView.findViewById(R.id.song_title);
            iconImageView=itemView.findViewById(R.id.song_icon);
        }
    }
}
