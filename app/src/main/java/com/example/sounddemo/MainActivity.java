package com.example.sounddemo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    Button playButton, stopButton;
    MediaPlayer mediaPlayer;
    SeekBar volumeSeekBar, scrubSeekBar;
    AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playButton = findViewById(R.id.PlayButton);
        stopButton = findViewById(R.id.StopButton);
        mediaPlayer = MediaPlayer.create(this, R.raw.melody);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        scrubSeekBar = findViewById(R.id.scrubSeekBar);

        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeSeekBar.setMax(maxVolume);
        volumeSeekBar.setProgress(currentVolume);

        scrubSeekBar.setMax(mediaPlayer.getDuration());


        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                Log.i("volume", String.valueOf(progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        scrubSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                mediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.start();
            }
        });



    }

    private void startTimer(){
                new Timer().scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        scrubSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                    }
                }, 0, 300);
    }

    public void playSound(View view){
        mediaPlayer.setLooping(true);
                mediaPlayer.start();
    }

    public void stopSound(View view){
        mediaPlayer.pause();
    }
}