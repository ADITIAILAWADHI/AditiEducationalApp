package com.ADITIAILAWADHI.aditieducationalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ADITIAILAWADHI.aditieducationalapp.helper.SoundManager;


public class SoundTestingActivity extends AppCompatActivity {

    private SoundManager soundManager;
    private int you_winSoundID;
    private int merengueSoundID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_testing);

        /* INITIALIZATION */
        soundManager = new SoundManager(this);
//        Button btnMarimba = findViewById(R.id.btnMarimba);
//        Button btnMerengue = findViewById(R.id.btnMerengue);

        /* START */
        //if you have to load many songs then load them in another thread
        you_winSoundID = soundManager.addSound(R.raw.you_win);
        merengueSoundID = soundManager.addSound(R.raw.merengue);


    }

    public void buttonClicked(View view) {
        Button b = (Button) view;
        String btnText = b.getText().toString();
        //if (soundManager !=null && btnText.equals("MARIMBA")) {
        if (btnText.equals("MARIMBA")) {
            soundManager.play(you_winSoundID, 1);
        }
        else {
            soundManager.play(merengueSoundID, 1);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(soundManager != null){
            soundManager.releaseSoundPool();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(soundManager != null){
            soundManager.releaseSoundPool();
        }
    }

}