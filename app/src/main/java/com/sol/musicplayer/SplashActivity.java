package com.sol.musicplayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class SplashActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = findViewById(R.id.splash_logo);
        ProgressBar progressBar = findViewById(R.id.pb);

        //fade in animations                  0-transparent to 1-visible
        Animation fadeIn = new AlphaAnimation(0,1);
        fadeIn.setDuration(1500);//will complete in 1.5 sec
        logo.startAnimation(fadeIn);
        progressBar.startAnimation(fadeIn);

        //delay splash screen for 2.5 seconds
        new Handler().postDelayed(() -> {

            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        },2500 );

    }
}