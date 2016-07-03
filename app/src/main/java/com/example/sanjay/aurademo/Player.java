package com.example.sanjay.aurademo;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;

public class Player extends AppCompatActivity implements View.OnClickListener {
    static MediaPlayer mp;
    ArrayList <File> mySongs;
    Uri uri;
    int position;
    SeekBar seekBar;
    ImageButton play_pause, forward, backward, prev, next;
    Thread updateSeekBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        play_pause=(ImageButton) findViewById(R.id.imgBtnPlay_pause);
        prev=(ImageButton) findViewById(R.id.imgBtnprevious);
        next=(ImageButton) findViewById(R.id.imgBtnNext);
        forward=(ImageButton) findViewById(R.id.imgBtnForward);
        backward=(ImageButton) findViewById(R.id.imgBtnBackward);
        play_pause.setOnClickListener(this);
        prev.setOnClickListener(this);
        next.setOnClickListener(this);
        forward.setOnClickListener(this);
        backward.setOnClickListener(this);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        updateSeekBar= new Thread(){
            @Override
            public void run(){
                int totalDuration= mp.getDuration();
                int currentPosition=0;
                seekBar.setMax(totalDuration);
                while(currentPosition< totalDuration){
                    try {
                        sleep(500);
                        currentPosition=mp.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        if(mp!=null)
        {
            mp.stop();
            mp.release();
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent= getIntent();
        Bundle bundle= intent.getExtras();
        mySongs =(ArrayList) bundle.getParcelableArrayList("songlist");
        position= bundle.getInt("pos",0);
        uri= Uri.parse(mySongs.get(position).toString());
        mp=MediaPlayer.create(getApplicationContext(),uri);
        mp.start();
        updateSeekBar.start();
        seekBar.setMax(mp.getDuration());
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mp.seekTo(seekBar.getProgress());
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch(id){
            case R.id.imgBtnPlay_pause:
                if(mp.isPlaying()){
                    play_pause.setBackgroundResource(R.drawable.pause);
                    mp.pause();

                }
                else {
                    play_pause.setBackgroundResource(R.drawable.play);
                    mp.start();
                }

                break;
            case R.id.imgBtnForward:
                mp.seekTo(mp.getCurrentPosition()+5000);
                break;
            case R.id.imgBtnBackward:
                mp.seekTo(mp.getCurrentPosition()-5000);
            case R.id.imgBtnNext:
                mp.stop();
                mp.release();
                position=(position+1)%mySongs.size();
                uri= Uri.parse(mySongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                break;
            case R.id.imgBtnprevious:
                mp.stop();
                mp.release();
                position=(position-1<0)? mySongs.size()-1: position-1;
                uri= Uri.parse(mySongs.get(position).toString());
                mp=MediaPlayer.create(getApplicationContext(),uri);
                mp.start();
                break;




        }
    }
}
